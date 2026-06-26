package io.droidbot.emolga.ui.wordle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import io.github.dr0idbot.vlucide.LucideIcon;

@Route("wordle")
@PageTitle("Wordle")
@Menu(title = "Wordle", order = 2)
public class WordleView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(WordleView.class);
    private static final int WORD_LENGTH = 5;
    private static final int MAX_TURNS = 6;

    private static final String CELL_GREEN = "wordle-cell--green";
    private static final String CELL_YELLOW = "wordle-cell--yellow";
    private static final String CELL_GRAY = "wordle-cell--gray";

    private final Span[][] cells = new Span[MAX_TURNS][WORD_LENGTH];
    private final TextField input;
    private final Button enter;
    private int turn;

    public WordleView() {
        addClassName("wordle-view");

        var grid = new VerticalLayout();
        grid.addClassName("wordle-grid");

        for (int r = 0; r < MAX_TURNS; r++) {
            var row = new HorizontalLayout();
            row.addClassName("wordle-row");
            for (int c = 0; c < WORD_LENGTH; c++) {
                cells[r][c] = createCell();
                row.add(cells[r][c]);
            }
            grid.add(row);
        }

        input = new TextField();
        input.addClassName("wordle-input");
        input.setMaxLength(WORD_LENGTH);
        input.setValueChangeMode(ValueChangeMode.EAGER);
        input.addValueChangeListener(e -> validateInput(e.getValue()));

        enter = new Button("Enter", LucideIcon.ARROW_RIGHT.create());
        enter.addClassName("wordle-enter");
        enter.setEnabled(false);
        enter.addClickListener(e -> processInput());

        var controls = new HorizontalLayout(input, enter);
        controls.addClassName("wordle-controls");
        controls.setAlignItems(Alignment.BASELINE);

        add(grid, controls);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    private void validateInput(String value) {
        if (value != null && !value.trim().isBlank()) {
            input.setValue(value.toUpperCase());
            enter.setEnabled(value.length() == WORD_LENGTH);
        } else {
            enter.setEnabled(false);
        }
    }

    private void processInput() {
        evaluateInput();
        turn++;
        input.clear();
        input.focus();
        enter.setEnabled(false);

        if (turn >= MAX_TURNS) {
            input.setEnabled(false);
            enter.setEnabled(false);
        }
    }

    private void evaluateInput() {
        var word = getTodaysWord();
        var guess = input.getValue();
        var remaining = new int[26];
        var results = new CellResult[WORD_LENGTH];

        for (var ch : word.toCharArray()) {
            remaining[ch - 'A']++;
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (guess.charAt(i) == word.charAt(i)) {
                results[i] = CellResult.GREEN;
                remaining[guess.charAt(i) - 'A']--;
            }
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (results[i] == CellResult.GREEN) {
                continue;
            }
            int idx = guess.charAt(i) - 'A';
            if (remaining[idx] > 0) {
                results[i] = CellResult.YELLOW;
                remaining[idx]--;
            } else {
                results[i] = CellResult.GRAY;
            }
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            var cell = cells[turn][i];
            cell.setText(String.valueOf(guess.charAt(i)));

            String className = switch (results[i]) {
                case GREEN -> CELL_GREEN;
                case YELLOW -> CELL_YELLOW;
                case GRAY -> CELL_GRAY;
            };
            cell.addClassName(className);
        }

        LOG.info("Word: {}, Guess: {}, Result: {}", word, guess, results);
    }

    private String getTodaysWord() {
        return "CLOUD";
    }

    private static Span createCell() {
        var cell = new Span();
        cell.addClassName("wordle-cell");
        return cell;
    }

    private enum CellResult {
        GREEN, YELLOW, GRAY
    }

}
