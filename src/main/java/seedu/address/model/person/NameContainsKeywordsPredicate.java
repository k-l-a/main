package seedu.address.model.person;

import static seedu.address.model.person.PredicateSyntax.BOOLEAN_AND;
import static seedu.address.model.person.PredicateSyntax.BOOLEAN_NOT;
import static seedu.address.model.person.PredicateSyntax.BOOLEAN_OR;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} matches the keywords according to the
 * boolean operator (AND, OR, NOT). If boolean operator is not one of those three values, defaults to OR.
 */
public class NameContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;
    private String boolOperator;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        //defaults to OR search
        this(keywords, BOOLEAN_OR);
    }

    public NameContainsKeywordsPredicate(List<String> keywords, String boolOperator) {
        this.keywords = keywords;
        if (isBoolOperator(boolOperator)) {
            this.boolOperator = boolOperator;
        } else {
            this.boolOperator = BOOLEAN_OR;
        }
    }

    private boolean isBoolOperator(String boolOperator) {
        return boolOperator.equals(BOOLEAN_OR)
                || boolOperator.equals(BOOLEAN_AND)
                || boolOperator.equals(BOOLEAN_NOT);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        if (boolOperator.equals(BOOLEAN_AND)) {
            return keywords.stream()
                    .allMatch(keywords -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keywords));
        } else if (boolOperator.equals(BOOLEAN_NOT)) {
            return keywords.stream()
                    .noneMatch(keywords -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keywords));
        } else {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsKeywordsPredicate) other).keywords)) // state check
                && this.boolOperator.equals(((NameContainsKeywordsPredicate) other).boolOperator);
    }

}
