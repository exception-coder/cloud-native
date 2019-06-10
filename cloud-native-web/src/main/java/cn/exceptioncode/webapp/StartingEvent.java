package cn.exceptioncode.webapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;


/**
 *
 * @author zhangkai 
 */
@Slf4j
public class StartingEvent implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
      log.info("ApplicationListener by ApplicationStartingEvent, Application Starting ...");
    }
}
