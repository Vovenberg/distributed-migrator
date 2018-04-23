package com.killprojects.migrator.job;

import com.killprojects.migrator.dto.RecordId;
import com.killprojects.migrator.dto.TransferResult;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.List;

@Service
public class ErrorProcessor<T> {

    @Setter
    private List<String> transferErrors;

    //@Value("resend-count")
    private int resendCount;

    public TransferResult processIfError(TransferResult result, Tuple2<RecordId, T> idAndObject) {
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
