package com.pch.kenofrontend.utilities;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class CredentialProviderFactory {
	
	public static CredentialsProvider Get(String credentialProviderType)
	{
		CredentialsProvider credsProvider = null;
		switch(credentialProviderType.toUpperCase())
		{
		case "NTLM":
		
			/*NTCredentials ntCreds = new NTCredentials(
					PropertiesReader.getInstance().lottoContestAdminUserName,
					PropertiesReader.getInstance().lottoContestAdminPassword,
					PropertiesReader.getInstance().lottoContestAdminHost,
					PropertiesReader.getInstance().lottoContestAdminDomain);

			credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials( new AuthScope(PropertiesReader.getInstance().lottoContestAdminHost,80), ntCreds );*/					
			break;
			
		default:
			break;
		}
		return credsProvider;
	}

}
