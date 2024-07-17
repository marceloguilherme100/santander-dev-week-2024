package me.dio.santanderdevweek2023;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SantanderDevWeek2023ApplicationTests {

	@Test
	@DisplayName("Teste inicial")
	void main() {
		Application.main(new String[] {});
	}

}
