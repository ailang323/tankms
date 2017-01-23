package com.bytecompute.tankms.annotation;

import com.bytecompute.tankms.TankMessageServiceBootstrapConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by Longer on 2016/12/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TankMessageServiceBootstrapConfiguration.class)
public @interface EnableTankMessageServiceConfig {
}
