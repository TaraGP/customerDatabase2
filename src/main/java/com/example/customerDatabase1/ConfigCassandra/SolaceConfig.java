package com.example.customerDatabase1.ConfigCassandra;


import com.solacesystems.jms.SolConnectionFactory;
import com.solacesystems.jms.SolJmsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class SolaceConfig {


    @Value("${solace.jms.host}")
    private String host;

    @Value("${solace.jms.msgVpn}")
    private String msgVpn;

    @Value("${solace.jms.clientUsername}")
    private String username;

    @Value("${solace.jms.clientPassword}")
    private String password;


    public SolConnectionFactory solConnectionFactory() throws Exception {
        SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setVPN(msgVpn);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        CachingConnectionFactory ccf=null;
        try {
            ccf = new CachingConnectionFactory(solConnectionFactory());

        }
        catch (Exception e){

        }
        return new JmsTemplate(ccf);

    }
}
