package validators;

import domain.Prietenie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ValidarePrietenie implements Validator<Prietenie> {
    @Override
    public void validate(Prietenie prietenie) throws ValidationException {
        long IdPrieten1 = prietenie.getId().getLeft();
        long IdPrieten2 = prietenie.getId().getRight();
        if (IdPrieten1 == IdPrieten2) throw new ValidationException("a friendship cannot be between the same person");
        if (prietenie.getDate().isAfter(LocalDateTime.now())) throw new ValidationException("invalide friendship date");

    }
}
