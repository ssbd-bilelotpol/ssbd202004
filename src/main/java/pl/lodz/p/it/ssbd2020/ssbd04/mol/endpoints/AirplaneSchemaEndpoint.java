package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirplaneSchemaDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirplaneSchemaListDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas AirplaneSchema.
 */
@Local
public interface AirplaneSchemaEndpoint extends TransactionStarter {

    /**
     * Tworzy nowy schemat samolotu z ustalonymi miejscami.
     *
     * @param airplaneSchemaDto dane schematu samolotu.
     * @return stworzony schemat samolotu.
     * @throws AppBaseException gdy operacja tworzenia nie powiedzie się.
     */
    AirplaneSchemaDto create(AirplaneSchemaDto airplaneSchemaDto) throws AppBaseException;

    /**
     * Znajduje schemat samolotu na podstawie identyfikatora.
     *
     * @param id identyfikator schematu samolotu.
     * @return znaleziony schemat samolotu.
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź schemat nie został znaleziony.
     */
    AirplaneSchemaDto findById(Long id) throws AppBaseException;

    /**
     * Aktualizuje schemat samolotu.
     *
     *
     * @param id
     * @param airplaneSchemaDto dane schematu samolotu.
     * @return zaaktualizowany schemat samolotu.
     * @throws AppBaseException gdy schemat jest już używany, zajdzie problem konkurencyjnej modyfikacji, bądź schemat nie istnieje.
     */
    void update(Long id, AirplaneSchemaDto airplaneSchemaDto) throws AppBaseException;

    /**
     * Usuwa schemat samolotu.
     *
     * @param id identyfikator schematu samolotu.
     * @throws AppBaseException gdy schemat jest używany przez pewien lot, lub gdy schemat nie istnieje.
     */
    void delete(Long id) throws AppBaseException;

    /**
     * Szuka schematów samolotu po nazwie.
     * @param name część nazwy schematu samolotu
     * @return lista obiektów reprezentujących schematy samolotu
     */
    List<AirplaneSchemaListDto> findByName(String name) throws AppBaseException;
}
