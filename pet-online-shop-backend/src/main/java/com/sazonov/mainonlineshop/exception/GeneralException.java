package com.sazonov.mainonlineshop.exception;

import com.sazonov.mainonlineshop.enums.ResultEnum;

public class GeneralException extends RuntimeException{
        private Integer code;

        public GeneralException(ResultEnum resultEnum){
            super(resultEnum.getMessage());
            this.code = resultEnum.getCode();
        }

        public GeneralException(Integer code, String message){
            super(message);
            this.code = code;
        }
}
