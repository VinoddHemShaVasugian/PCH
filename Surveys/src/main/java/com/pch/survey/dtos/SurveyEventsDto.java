package com.pch.survey.dtos;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyEventsDto {
	@JsonProperty("CBL")
	private String cbl;
	@JsonProperty("EDID")
	private String edid;
	@JsonProperty("ExperienceDeviceType")
	private String experienceDeviceType;
	@JsonProperty("GMT")
	private String gmt;
	@JsonProperty("IsTestData")
	private boolean isTestData;
	@JsonProperty("OAT")
	private String oat;
	@JsonProperty("ReceivedAt")
	private String receivedAt;
	@JsonProperty("ReferenceId")
	private String referenceId;
	@JsonProperty("RefererURL")
	private String refererURL;
	@JsonProperty("SessionToken")
	private String sessionToken;
	@JsonProperty("TransactionId")
	private String transactionId;
	@JsonProperty("TSRC")
	private String tsrc;
	@JsonProperty("TSRC2")
	private String tsrc2;
	@JsonProperty("UserSignedIn")
	private String userSignedIn;
	@JsonProperty("GaSessionId")
	private String gaSessionId;
	@JsonProperty("GaClientId")
	private String gaClientId;
	@JsonProperty("Platform")
	private String platform;
	@JsonProperty("ContestEntriesGranted")
	private ContestEntriesGranted contestEntriesGranted;
	@JsonProperty("Duration")
	private BigDecimal duration;
	@JsonProperty("LengthInMinutes")
	private String lengthInMinutes;
	@JsonProperty("MinimumGrossCPI")
	private String minimumGrossCPI;
	@JsonProperty("Payout")
	private BigDecimal payout;
	@JsonProperty("PlacementId")
	private String placementId;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("TokensGranted")
	private TokensGranted tokensGranted;
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonProperty("SurveyId")
	private String surveyId;

	@JsonProperty("SurveyReward")
	private SurveyReward surveyReward;
	@JsonProperty("SurveyType")
	private String surveyType;
	@JsonProperty("DisplayAds")
	private int displayAds;
	@JsonProperty("PchSurveyId")
	private String pchSurveyId;
	@JsonProperty("IntegrationMode")
	private String integrationMode;
	@JsonProperty("MidSessionKey")
	private String midSessionKey;
	@JsonProperty("OriginalSupplier")
	private String originalSupplier;

	@JsonProperty("CategoryId")
	private String categoryId;
	@JsonProperty("CategoryName")
	private String categoryName;
	@JsonProperty("IsMission")
	private String isMission;
	@JsonProperty("IsPath")
	private String isPath;
	@JsonProperty("MidOrder")
	private String midOrder;
	@JsonProperty("MissionResetType")
	private String missionResetType;

	@JsonProperty("ParentMidSessionKey")
	private String parentMidSessionKey;
	@JsonProperty("Buyer")
	private String buyer;

	public String getCbl() {
		return cbl;
	}

	public void setCbl(String cbl) {
		this.cbl = cbl;
	}

	public String getEdid() {
		return edid;
	}

	public void setEdid(String edid) {
		this.edid = edid;
	}

	public String getExperienceDeviceType() {
		return experienceDeviceType;
	}

	public void setExperienceDeviceType(String experienceDeviceType) {
		this.experienceDeviceType = experienceDeviceType;
	}

	public String getGmt() {
		return gmt;
	}

	public void setGmt(String gmt) {
		this.gmt = gmt;
	}

	public boolean isTestData() {
		return isTestData;
	}

	public void setTestData(boolean testData) {
		isTestData = testData;
	}

	public String getOat() {
		return oat;
	}

	public void setOat(String oat) {
		this.oat = oat;
	}

	public String getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(String receivedAt) {
		this.receivedAt = receivedAt;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getRefererURL() {
		return refererURL;
	}

	public void setRefererURL(String refererURL) {
		this.refererURL = refererURL;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTsrc() {
		return tsrc;
	}

	public void setTsrc(String tsrc) {
		this.tsrc = tsrc;
	}

	public String getTsrc2() {
		return tsrc2;
	}

	public void setTsrc2(String tsrc2) {
		this.tsrc2 = tsrc2;
	}

	public String getUserSignedIn() {
		return userSignedIn;
	}

	public void setUserSignedIn(String userSignedIn) {
		this.userSignedIn = userSignedIn;
	}

	public String getGaSessionId() {
		return gaSessionId;
	}

	public void setGaSessionId(String gaSessionId) {
		this.gaSessionId = gaSessionId;
	}

	public ContestEntriesGranted getContestEntriesGranted() {
		return contestEntriesGranted;
	}

	public void setContestEntriesGranted(ContestEntriesGranted contestEntriesGranted) {
		this.contestEntriesGranted = contestEntriesGranted;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public String getLengthInMinutes() {
		return lengthInMinutes;
	}

	public void setLengthInMinutes(String lengthInMinutes) {
		this.lengthInMinutes = lengthInMinutes;
	}

	public String getMinimumGrossCPI() {
		return minimumGrossCPI;
	}

	public void setMinimumGrossCPI(String minimumGrossCPI) {
		this.minimumGrossCPI = minimumGrossCPI;
	}

	public BigDecimal getPayout() {
		return payout;
	}

	public void setPayout(BigDecimal payout) {
		this.payout = payout;
	}

	public String getPlacementId() {
		return placementId;
	}

	public void setPlacementId(String placementId) {
		this.placementId = placementId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TokensGranted getTokensGranted() {
		return tokensGranted;
	}

	public void setTokensGranted(TokensGranted tokensGranted) {
		this.tokensGranted = tokensGranted;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public SurveyReward getSurveyReward() {
		return surveyReward;
	}

	public void setSurveyReward(SurveyReward surveyReward) {
		this.surveyReward = surveyReward;
	}

	public String getSurveyType() {
		return surveyType;
	}

	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}

	public int getDisplayAds() {
		return displayAds;
	}

	public void setDisplayAds(int displayAds) {
		this.displayAds = displayAds;
	}

	public String getPchSurveyId() {
		return pchSurveyId;
	}

	public void setPchSurveyId(String pchSurveyId) {
		this.pchSurveyId = pchSurveyId;
	}

	public String getIntegrationMode() {
		return integrationMode;
	}

	public void setIntegrationMode(String integrationMode) {
		this.integrationMode = integrationMode;
	}

	public String getMidSessionKey() {
		return midSessionKey;
	}

	public void setMidSessionKey(String midSessionKey) {
		this.midSessionKey = midSessionKey;
	}

	public String getOriginalSupplier() {
		return originalSupplier;
	}

	public void setOriginalSupplier(String originalSupplier) {
		this.originalSupplier = originalSupplier;
	}

	public String getParentMidSessionKey() {
		return parentMidSessionKey;
	}

	public void setParentMidSessionKey(String parentMidSessionKey) {
		this.parentMidSessionKey = parentMidSessionKey;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public static class ContestEntriesGranted {
		private Integer amount;
		private String key;

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}

	public static class TokensGranted {
		private Integer amount;
		private String description;

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	public static class SurveyReward {
		private Tokens tokens;
		private Entries entries;

		public Tokens getTokens() {
			return tokens;
		}

		public void setTokens(Tokens tokens) {
			this.tokens = tokens;
		}

		public Entries getEntries() {
			return entries;
		}

		public void setEntries(Entries entries) {
			this.entries = entries;
		}

		public static class Tokens {
			private Token complete;
			private Token incomplete;

			public Token getComplete() {
				return complete;
			}

			public void setComplete(Token complete) {
				this.complete = complete;
			}

			public Token getIncomplete() {
				return incomplete;
			}

			public void setIncomplete(Token incomplete) {
				this.incomplete = incomplete;
			}

			public static class Token {
				private Integer amount;
				private String description;

				public Integer getAmount() {
					return amount;
				}

				public void setAmount(Integer amount) {
					this.amount = amount;
				}

				public String getDescription() {
					return description;
				}

				public void setDescription(String description) {
					this.description = description;
				}
			}
		}

		public static class Entries {
			private Entry complete;
			private Entry incomplete;

			public Entry getComplete() {
				return complete;
			}

			public void setComplete(Entry complete) {
				this.complete = complete;
			}

			public Entry getIncomplete() {
				return incomplete;
			}

			public void setIncomplete(Entry incomplete) {
				this.incomplete = incomplete;
			}

			public static class Entry {
				private Integer amount;
				private String key;

				public Integer getAmount() {
					return amount;
				}

				public void setAmount(Integer amount) {
					this.amount = amount;
				}

				public String getKey() {
					return key;
				}

				public void setKey(String key) {
					this.key = key;
				}
			}
		}
	}

	public static void main(String[] args) {
	}

}