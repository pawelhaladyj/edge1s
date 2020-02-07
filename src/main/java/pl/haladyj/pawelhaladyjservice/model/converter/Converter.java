package pl.haladyj.pawelhaladyjservice.model.converter;

public interface Converter<Model, Dto> {
    Model toEntity(Dto dto);
    Dto toDto(Model model);
}
