package io.droidbot.emolga.ui;

import java.util.List;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
public class DummyView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public DummyView() {

		EmolgaGrid<Person> emolgaGrid = new EmolgaGrid<>(Person.class, "People");

		Grid<Person> grid = emolgaGrid.getGrid();

		grid.addColumn(Person::getName).setHeader("Name");

		grid.addColumn(Person::getAge).setHeader("Age");

		List<Person> people = List.of(new Person("Alice", 25), new Person("Bob", 30), new Person("Charlie", 35));

		grid.setItems(people);

		emolgaGrid.setRowCount(people.size());
		emolgaGrid.setHeight(400, Unit.PIXELS);

		add(emolgaGrid);

		setSizeFull();
	}

	public static class Person {

		private String name;
		private int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}
	}
}