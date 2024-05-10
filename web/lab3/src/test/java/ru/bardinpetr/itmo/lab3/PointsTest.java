// Generated by delombok at Thu May 09 13:42:06 MSK 2024
package ru.bardinpetr.itmo.lab3;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.bardinpetr.itmo.lab3.data.dao.impl.UserDAO;
import ru.bardinpetr.itmo.lab3.data.models.Point;
import ru.bardinpetr.itmo.lab3.data.models.PointResult;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static ru.bardinpetr.itmo.lab3.MockFactory.createPointResult;
import static ru.bardinpetr.itmo.lab3.MockFactory.createUser;

public class PointsTest {
	@Test
	void testPoint() {
		var p = Point.of(2.0, 5.0);
		p.setX(3.0);
		p.setY(4.0);
		var p2 = p.scale(3.0);

		assertThat(p2.getX()).isCloseTo(9.0, Offset.offset(1e-6));
		assertThat(p2.getY()).isCloseTo(12.0, Offset.offset(1e-6));
	}
}
