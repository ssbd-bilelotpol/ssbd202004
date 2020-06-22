package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.MESSAGE_SIGNER_OPERATION;

/**
 * Opakowuje RuntimeException w celu przetworzenia wyjątków rzucanych przez aplikację
 */
public class AppBaseRuntimeException extends RuntimeException {
    public AppBaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Wyjątek reprezentujący błąd algorytmu podpisującego sygnaturę obiektów potrzebnych
     * do mechanizmów blokad optymistycznych
     *
     * @param cause wyjątek powodujący błąd
     * @return wyjątek typu AppBaseRuntimeException
     */
    public static AppBaseRuntimeException signingAlgorithmError(Throwable cause) {
        return new AppBaseRuntimeException(MESSAGE_SIGNER_OPERATION, cause);
    }
}
