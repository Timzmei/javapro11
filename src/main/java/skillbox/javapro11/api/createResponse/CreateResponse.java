package skillbox.javapro11.api.createResponse;

import skillbox.javapro11.api.response.PersonResponse;
import skillbox.javapro11.model.entity.Person;

public class CreateResponse
{
    public static PersonResponse createPersonResponse(Person person, String token) {

        /**
         * Не закончено. Закоментированный код ниже, вопрос с этими переменными, задам на встречи
         */
        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(person.getId());
        personResponse.setFirstName(person.getFirstName());
        personResponse.setLastName(person.getLastName());

        //personResponse.setRegistrationDate(person.getRegistrationDate());
        //personResponse.setBirthDate(person.getBirthday());
        personResponse.setEmail(person.getEmail());
        personResponse.setPhone(person.getPhone());
        personResponse.setPhoto(person.getPhoto());
        personResponse.setAbout(person.getAbout());

        /*LocationOrLanguageDTO locationOrLanguageDTO = new LocationOrLanguageDTO();
        personResponse.setCity(locationOrLanguageDTO);*/
        personResponse.setMessagesPermission(person.getPermissionMessage());
        //personResponse.setLastOnlineTime(person.getLastTimeOnline());
        personResponse.setBlocked(person.isBlocked());
        personResponse.setToken(token);

        return personResponse;
    }
}
