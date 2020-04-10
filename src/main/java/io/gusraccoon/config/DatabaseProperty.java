package io.gusraccoon.config;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("datasource")
public class DatabaseProperty {
    private String url;
    private List<Slave> slaveList;

    private String username;
    private String password;

    @Getter
    @Setter
    public static class Slave {
        private String slavename;
        private String slaveurl;

		public void setSlavename(String slavename) {
			this.slavename = slavename;
		}

		public String getSlaveurl() {
			return slaveurl;
		}

		public void setSlaveurl(String slaveurl) {
			this.slaveurl = slaveurl;
		}

		public String getSlavename() {
			return slavename;
		}
    }

	public String getPassword() {
		return password;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSlaveList(List<Slave> slaveList) {
		this.slaveList = slaveList;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getUrl() {
		return url;
	}

	public List<Slave> getSlaveList() {
		return slaveList;
	}
}