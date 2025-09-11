package ait.cohort63.shop.exeption_handling;

import java.util.List;
import java.util.Objects;

public class ValidationResponse {
    private List<String> errors;

    public ValidationResponse(List<String> errors) {
        this.errors = errors;
    }
    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ValidationResponse that = (ValidationResponse) o;
        return Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(errors);
    }

    @Override
    public String toString() {
        return "Response : errors=- " + errors;
    }
}
