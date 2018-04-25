package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.TransferResult;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class ErrorProcessor<T> implements Serializable {

    @Setter
    private List<String> transferErrors;

    //@Value("resend-count")
    private int resendCount;

    public TransferResult processIfError(TransferResult result, T idAndObject) {
        // TODO: 23.04.2018 обработка разных типов ошибок

        //если транспортная ошибка
        if (false) {
            while (resendCount > 0) {
                //снова отправляем
                resendCount--;
            }
        }
        return result;
    }
}
