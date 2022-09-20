package cl.jp.calculator;

import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

@Service
public class Calculator {
	@Cacheable("sum")
	public int sum(int a, int b) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return a + b;
	}
}
