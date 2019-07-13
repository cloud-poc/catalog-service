package org.akj.springboot.catalog;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class })
class EurekaClientTest {

	@Autowired
	private EurekaClient client;

	@Test
	void test() {
		com.netflix.discovery.shared.Application instance = client.getApplication("config-service");
		instance.getInstances().forEach(ins -> System.out.println(ins.getHostName() + ":" + ins.getPort()));
		InstanceInfo instanceInfo = instance.getInstances().get(0);

		Assertions.assertNotNull(instanceInfo);
	}

}
