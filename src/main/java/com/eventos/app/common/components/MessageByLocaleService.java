package com.eventos.app.common.components;

import com.eventos.app.common.interfaces.InterfaceMessageByLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
/**
 *  Retorna mensagens com base no idioma selecionado pelo usuário
 * **/
@Component
public class MessageByLocaleService implements InterfaceMessageByLocaleService {

    private MessageSource messageSource;

    @Autowired
    public MessageByLocaleService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public String getMessage(String id) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id,null,locale);
    }
}
