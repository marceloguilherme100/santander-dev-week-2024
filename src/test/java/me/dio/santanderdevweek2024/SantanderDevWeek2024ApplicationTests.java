package me.dio.santanderdevweek2024;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SantanderDevWeek2024ApplicationTests {

	@Test
	@DisplayName("Teste inicial")
	void main() {
		Application.main(new String[] {});
	}

}
