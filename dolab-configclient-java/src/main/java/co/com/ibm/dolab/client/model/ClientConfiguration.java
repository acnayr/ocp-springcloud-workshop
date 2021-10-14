package co.com.ibm.dolab.client.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties("app")
@ToString(onlyExplicitlyIncluded = true )
public class ClientConfiguration implements Serializable {


    @ToString.Include
    private boolean enableAFeature;

    @ToString.Include
    private String newFeatureTitle;

    @ToString.Include
    private String newFeatureDesc;

}