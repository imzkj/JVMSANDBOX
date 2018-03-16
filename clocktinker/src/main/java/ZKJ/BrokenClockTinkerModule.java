package ZKJ;

import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.ProcessControlException;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.filter.NameRegexFilter;
import com.alibaba.jvm.sandbox.api.http.Http;
import com.alibaba.jvm.sandbox.api.listener.EventListener;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;

import javax.annotation.Resource;

/**
 * 修复损坏的钟模块
 */
@Information(id = "broken-clock-tinker")
public class BrokenClockTinkerModule implements Module {

	@Resource
	private ModuleEventWatcher moduleEventWatcher;

	@Http("/repairCheckState")
	public void repairCheckState() {

		moduleEventWatcher.watch(

				// 匹配到Clock$BrokenClock#checkState()
				new NameRegexFilter("ZKJ\\.Clock\\$BrokenClock", "checkState"),

				// 监听THROWS事件并且改变原有方法抛出异常为正常返回
				new EventListener() {
					@Override
					public void onEvent(Event event) throws Throwable {
						// 立即返回
						ProcessControlException.throwReturnImmediately(null);
					}
				},

				// 指定监听的事件为抛出异常
				Event.Type.THROWS
		);

	}

	@Http("/repairDelay")
	public void repairDelay() {

		moduleEventWatcher.watch(

				// 匹配到Clock$BrokenClock#checkState()
				new NameRegexFilter("ZKJ\\.Clock\\$BrokenClock", "delay"),

				// 监听THROWS事件并且改变原有方法抛出异常为正常返回
				new EventListener() {
					@Override
					public void onEvent(Event event) throws Throwable {

						// 在这里延时1s
						Thread.sleep(1000L);

						// 然后立即返回，因为监听的是BEFORE事件，所以此时立即返回，方法体将不会被执行
						ProcessControlException.throwReturnImmediately(null);
					}
				},

				// 指定监听的事件为方法执行前
				Event.Type.BEFORE

		);

	}

}