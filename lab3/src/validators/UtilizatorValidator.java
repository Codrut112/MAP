package validators;


import domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String erori = "";
        if (entity.getId() == null) erori += "id vid ";
        if (entity.getId() < 0) erori += "id negativ";
        if (entity.getLastName().isEmpty()) erori += "missing last name ";
        if (entity.getFirstName().isEmpty()) erori += "missing first name ";
        if (!erori.isEmpty()) throw new ValidationException(erori);
    }
}

