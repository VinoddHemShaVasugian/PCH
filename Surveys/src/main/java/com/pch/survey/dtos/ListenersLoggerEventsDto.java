package com.pch.survey.dtos;

public class ListenersLoggerEventsDto {
    private String EventTime;
    private boolean IsTestData;
    private Client Client;
    private Source Source;
    private Session Session;
    private String Type;
    private String ReferenceId;
    private String TransactionId;
    private Data Data;

    public ListenersLoggerEventsDto() {
    	
    	Client = new Client();
    	Source = new Source();
    	Session = new Session();
    	Data = new Data();
    	
    }
    
    public String getEventTime() {
        return EventTime;
    }
     public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }
     public boolean isTestData() {
        return IsTestData;
    }
     public void setTestData(boolean testData) {
        IsTestData = testData;
    }
     public Client getClient() {
        return Client;
    }
     public void setClient(Client client) {
        Client = client;
    }
     public Source getSource() {
        return Source;
    }
     public void setSource(Source source) {
        Source = source;
    }
     public Session getSession() {
        return Session;
    }
     public void setSession(Session session) {
        Session = session;
    }
     public String getType() {
        return Type;
    }
     public void setType(String type) {
        Type = type;
    }
     public String getReferenceId() {
        return ReferenceId;
    }
     public void setReferenceId(String referenceId) {
        ReferenceId = referenceId;
    }
     public String getTransactionId() {
        return TransactionId;
    }
     public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }
     public Data getData() {
        return Data;
    }
     public void setData(Data data) {
        Data = data;
    }
     public static class Client {
        private String DeviceType;
        private String IpAddress;
        private String UserAgent;
        private String UserAgentId;
         public String getDeviceType() {
            return DeviceType;
        }
         public void setDeviceType(String deviceType) {
            DeviceType = deviceType;
        }
         public String getIpAddress() {
            return IpAddress;
        }
         public void setIpAddress(String ipAddress) {
            IpAddress = ipAddress;
        }
         public String getUserAgent() {
            return UserAgent;
        }
         public void setUserAgent(String userAgent) {
            UserAgent = userAgent;
        }
         public String getUserAgentId() {
            return UserAgentId;
        }
         public void setUserAgentId(String userAgentId) {
            UserAgentId = userAgentId;
        }
    }
     public static class Source {
        private String EmailVendorId;
        private String ForeignSource;
        private String IpAddress;
        private String OriginatingUrl;
        private String TrackingToken;
        private String CBL;
         public String getEmailVendorId() {
            return EmailVendorId;
        }
         public void setEmailVendorId(String emailVendorId) {
            EmailVendorId = emailVendorId;
        }
         public String getForeignSource() {
            return ForeignSource;
        }
         public void setForeignSource(String foreignSource) {
            ForeignSource = foreignSource;
        }
         public String getIpAddress() {
            return IpAddress;
        }
         public void setIpAddress(String ipAddress) {
            IpAddress = ipAddress;
        }
         public String getOriginatingUrl() {
            return OriginatingUrl;
        }
         public void setOriginatingUrl(String originatingUrl) {
            OriginatingUrl = originatingUrl;
        }
         public String getTrackingToken() {
            return TrackingToken;
        }
         public void setTrackingToken(String trackingToken) {
            TrackingToken = trackingToken;
        }
         public String getCBL() {
            return CBL;
        }
         public void setCBL(String CBL) {
            this.CBL = CBL;
        }
    }
     public static class Session {
        private String SessionToken;
        private String MidSessionKey;
        private String ParentMidSessionKey;
        private String GaSessionId;
         public String getSessionToken() {
            return SessionToken;
        }
         public void setSessionToken(String sessionToken) {
            SessionToken = sessionToken;
        }
         public String getMidSessionKey() {
            return MidSessionKey;
        }
         public void setMidSessionKey(String midSessionKey) {
            MidSessionKey = midSessionKey;
        }
         public String getParentMidSessionKey() {
            return ParentMidSessionKey;
        }
         public void setParentMidSessionKey(String parentMidSessionKey) {
            ParentMidSessionKey = parentMidSessionKey;
        }
         public String getGaSessionId() {
            return GaSessionId;
        }
         public void setGaSessionId(String gaSessionId) {
            GaSessionId = gaSessionId;
        }
    }
     public static class Data {
    	 
    	  
    	 
        private String ExperienceDeviceType;
        private String GMT;
        private String OAT;
        private String ReceivedAt;
        private String RefererURL;
        private String TSRC;
        private String TSRC2;
        private String UserSignedIn;
        private ContestEntriesGranted ContestEntriesGranted;
        private String Duration;
        private String LengthInMinutes;
        private String MinimumGrossCPI;
        private String Payout;
        private String PlacementId;
        private String Status;
        private TokensGranted TokensGranted;
        private int SurveyId;
        private SurveyReward SurveyReward;
        private String SurveyType;
        private int DisplayAds;
        private String PchSurveyId;
        private String IntegrationMode;
        private String OriginalSupplier;
        private String Buyer;
        
        private String Page;
        
        public String getPage() {
			return Page;
		}


		public void setPage(String page) {
			Page = page;
		}


		public Data() {
        	ContestEntriesGranted = new ContestEntriesGranted();
        	SurveyReward = new SurveyReward();
        	TokensGranted = new TokensGranted();
        }
        
    
        public String getExperienceDeviceType() {
            return ExperienceDeviceType;
        }
         public void setExperienceDeviceType(String experienceDeviceType) {
            ExperienceDeviceType = experienceDeviceType;
        }
         public String getGMT() {
            return GMT;
        }
         public void setGMT(String GMT) {
            this.GMT = GMT;
        }
         public String getOAT() {
            return OAT;
        }
         public void setOAT(String OAT) {
            this.OAT = OAT;
        }
         public String getReceivedAt() {
            return ReceivedAt;
        }
         public void setReceivedAt(String receivedAt) {
            ReceivedAt = receivedAt;
        }
         public String getRefererURL() {
            return RefererURL;
        }
         public void setRefererURL(String refererURL) {
            RefererURL = refererURL;
        }
         public String getTSRC() {
            return TSRC;
        }
         public void setTSRC(String TSRC) {
            this.TSRC = TSRC;
        }
         public String getTSRC2() {
            return TSRC2;
        }
         public void setTSRC2(String TSRC2) {
            this.TSRC2 = TSRC2;
        }
         public String getUserSignedIn() {
            return UserSignedIn;
        }
         public void setUserSignedIn(String userSignedIn) {
            UserSignedIn = userSignedIn;
        }
         public ContestEntriesGranted getContestEntriesGranted() {
            return ContestEntriesGranted;
        }
         public void setContestEntriesGranted(ContestEntriesGranted contestEntriesGranted) {
            ContestEntriesGranted = contestEntriesGranted;
        }
         public String getDuration() {
            return Duration;
        }
         public void setDuration(String duration) {
            Duration = duration;
        }
         public String getLengthInMinutes() {
            return LengthInMinutes;
        }
         public void setLengthInMinutes(String lengthInMinutes) {
            LengthInMinutes = lengthInMinutes;
        }
         public String getMinimumGrossCPI() {
            return MinimumGrossCPI;
        }
         public void setMinimumGrossCPI(String minimumGrossCPI) {
            MinimumGrossCPI = minimumGrossCPI;
        }
         public String getPayout() {
            return Payout;
        }
         public void setPayout(String payout) {
            Payout = payout;
        }
         public String getPlacementId() {
            return PlacementId;
        }
         public void setPlacementId(String placementId) {
            PlacementId = placementId;
        }
         public String getStatus() {
            return Status;
        }
         public void setStatus(String status) {
            Status = status;
        }
         public TokensGranted getTokensGranted() {
            return TokensGranted;
        }
         public void setTokensGranted(TokensGranted tokensGranted) {
            TokensGranted = tokensGranted;
        }
         public int getSurveyId() {
            return SurveyId;
        }
         public void setSurveyId(int surveyId) {
            SurveyId = surveyId;
        }
         public SurveyReward getSurveyReward() {
            return SurveyReward;
        }
         public void setSurveyReward(SurveyReward surveyReward) {
            SurveyReward = surveyReward;
        }
         public String getSurveyType() {
            return SurveyType;
        }
         public void setSurveyType(String surveyType) {
            SurveyType = surveyType;
        }
         public int getDisplayAds() {
            return DisplayAds;
        }
         public void setDisplayAds(int displayAds) {
            DisplayAds = displayAds;
        }
         public String getPchSurveyId() {
            return PchSurveyId;
        }
         public void setPchSurveyId(String pchSurveyId) {
            PchSurveyId = pchSurveyId;
        }
         public String getIntegrationMode() {
            return IntegrationMode;
        }
         public void setIntegrationMode(String integrationMode) {
            IntegrationMode = integrationMode;
        }
         public String getOriginalSupplier() {
            return OriginalSupplier;
        }
         public void setOriginalSupplier(String originalSupplier) {
            OriginalSupplier = originalSupplier;
        }
         public String getBuyer() {
            return Buyer;
        }
         public void setBuyer(String buyer) {
            Buyer = buyer;
        }
    }
     public static class ContestEntriesGranted {
        private int amount;
        private String key;
         public int getAmount() {
            return amount;
        }
         public void setAmount(int amount) {
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
        private int amount;
        private String description;
         public int getAmount() {
            return amount;
        }
         public void setAmount(int amount) {
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

        public SurveyReward() {
        	tokens = new Tokens();
        	entries = new Entries();
        	
        }
        
        
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
    }
     public static class Tokens {
        private Token complete;
        private Token incomplete;

        public Tokens() {
        	complete = new Token();
        	incomplete = new Token();
        }
        
        
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
    }
     public static class Token {
        private int amount;
        private String description;
         public int getAmount() {
            return amount;
        }
         public void setAmount(int amount) {
            this.amount = amount;
        }
         public String getDescription() {
            return description;
        }
         public void setDescription(String description) {
            this.description = description;
        }
    }
     public static class Entries {
        private Entry complete;
        private Entry incomplete;

        public Entries() {
        	complete = new Entry();
        	incomplete = new Entry();
        	
        	
        }
        
        
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
    }
     public static class Entry {
        private int amount;
        private String key;
         public int getAmount() {
            return amount;
        }
         public void setAmount(int amount) {
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