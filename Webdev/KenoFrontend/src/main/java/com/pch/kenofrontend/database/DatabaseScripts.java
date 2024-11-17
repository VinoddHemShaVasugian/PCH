package com.pch.kenofrontend.database;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class DatabaseScripts {

	public static final String getContestId = "SELECT Id FROM [PCHLottoGamePlay].[dbo].[Contests] "
			+ "where Name = '#ContestName#'";

	public static final String updateContestinContestActivities = 
			"update [PCHLottoGamePlay].[dbo].[ContestActivities]\r\n" + 
					"SET StartDate = '#StartDate#'\r\n" + 
					"Where ContestFk = #ContestID#";


	public static final String updateContestinBusinessUnit =
			"update [PCHLottoGamePlay].[dbo].[BusinessUnitAssociations]\r\n" + 
					"SET STARTDate = '#StartDate#'\r\n" + 
					"Where ContestFk = #ContestID#";

	public static final String updateStandAloneContestValue = 
			"Update [PCHLottoGamePlay].[dbo].[Contests] set StandAloneContest = #StandAloneContest# "
			+ "where id=#ContestID#";
	
	public static final String updateDrawingFrequencyModifier = 
			"Update [PCHLottoGamePlay].[dbo].[Contests] set "
			+ "DrawingFrequencyModifier = '#DrawingFrequencyModifier#' where id = #ContestID#";
	
	public static final String insertContestGameGroups = 
			"Insert into [PCHLottoGamePlay].[dbo].[ContestsGameGroups] "
			+ "values (#GameGroupID#,#GameID#)";
	
	public static final String getPlayerProfileFk = 			
			"SELECT * FROM [PCHLottoGamePlay].[dbo].[PlayerProfiles] \r\n" + 
			"	where MemberId = '#GMT#'";
	
	
	public static final String getPlayedGameDetails = 
			"SELECT * FROM [PCHLottoGamePlay].[dbo].[PlayGames] "
			+ "where PlayerProfileFk = #PlayerProfileFk#";
	
	
	public static final String updatePlayedGameDateforUser =
			"UPDATE [PCHLottoGamePlay].[dbo].[PlayGames] "
			+ "SET Date = '#PlayedDate# 16:38:22.000' "
			+ "where PlayerProfileFk = #PlayerProfileFk#";
	
	
	public static final String getDrawingFk = 
			"SELECT * FROM [PCHLottoGamePlay].[dbo].[DrawnGames] "
			+ "where PlayerProfileFk = #PlayerProfileFk#";
	
	public static final String updateDrawnGames =
			"update [PCHLottoGamePlay].[dbo].[DrawnGames] set "
			+ "Picks = '#Picks#' where id = #DrawnID#";
	
	public static final String insertDrawnAuxInfo =
			"insert into [PCHLottoGamePlay].[dbo].[DrawnGames_AuxInfo] values "
			+ "(#DrawnID#,'#UUID#',#PartialMatches#,1)";
	
	public static final String getWinnerDetails =
			"SELECT *  FROM [PCHLottoGamePlay].[dbo].[Winners] "
			+ "where DrawingFk = #DrawingFk#";
	
	public static final String getDrawingPayoutInfo =
			"SELECT *  FROM [PCHLottoGamePlay].[dbo].[DrawingPayoutInfo] "
			+ "where DrawingId = #DrawingId#";
	
	public static final String getClaimStatus = 
			"SELECT Top 1 DateCreated = max(DateCreated), ClaimStatus FROM "
			+ "[PCHLottoGamePlay].[dbo].[Claims] claims  where WinnerFk = #WinnerFk# group by ClaimStatus";
	
	public static final String getPlayGames = 
			"SELECT *  FROM [PCHLottoGamePlay].[dbo].[PlayGames] "
			+ "where GameCorelationId in (#GameID#)";
	
	public static final String getDrawnGames = 
			"SELECT *  FROM [PCHLottoGamePlay].[dbo].[DrawnGames] "
			+ "where DrawingFk=#DrawingFk#";
	
	public static final String getJackpotAmountinDrawingPayoutInfo =
			"SELECT *  FROM [PCHLottoGamePlay].[dbo].[DrawingPayoutInfo] where ContestId = #GameID# "
			+ "and NumberofMatches = 8 and SecondaryMatches = 1 order by DrawingID";
	
	public static final String cleanUpPartialCashDrawing = 
			"USE PCHLottoGamePlay\r\n" + 
			"Set Nocount On\r\n" + 
			"DECLARE @RC int\r\n" + 
			"DECLARE @DrawingDate datetime\r\n" + 
			"DECLARE @GameId int\r\n" + 
			"EXECUTE @RC = [PCHLottoGamePlay].[dbo].[GP_Test_CleanupPartialCashDrawing] \r\n" + 
			"   @DrawingDate = '#DrawingDate# 23:59:59'\r\n" + 
			"  ,@GameId = #ContestID#\r\n"+
			"Select @RC";
	
	public static final String insertWinningNumber = 
			"SET IDENTITY_INSERT [PCHLottoGamePlay].[dbo].[Drawings] ON\r\n" + 
			"Insert into [PCHLottoGamePlay].[dbo].[Drawings] ([Id],[GameId],[DrawingDate],[ActualDrawingDate]\r\n" + 
			"      ,[PreviousDrawingDate],[WinningNumbers],[EntryFileHash],[SeedType],[SeedValue],[PrizeAmount]\r\n" + 
			"      ,[IsDrawCompleted])values (#Id#,#GameID#,'#DrawingDate#','#ActualDrawingDate#'\r\n" + 
			"      ,'#PreviousDrawingDate#','#WinningNumbers#',NULL,NULL,NULL,#PrizeAmount#, #IsDrawCompleted#)\r\n" + 
			"SET IDENTITY_INSERT [PCHLottoGamePlay].[dbo].[Drawings] OFF";
	
	public static final String getWinningDetailsCount = 
			"SELECT count(*) Id FROM [PCHLottoGamePlay].[dbo].[Drawings]";
	
	public static final String insertPowerPrizePartialPayout =
			"SET IDENTITY_INSERT [PCHLottoGamePlay].[dbo].[PrizePayouts] ON\r\n" + 
			"Insert into [PCHLottoGamePlay].[dbo].[PrizePayouts] ([Id],[GameId],[GiveawayNumber],[GiveawayAmount],[GiveawayTotal]\r\n" + 
			"      ,[ContestKey],[NumberOfMatches],[SecondaryMatches],[PrizeAmount],[StartDate],[EndDate],[SupercedeDate])\r\n" + 
			"      values (#Id#,#GameId#,#GiveawayNumber#,#GiveawayAmount#,#GiveawayTotal#,'#ContestKey#',#NumberOfMatches#,"
			+ "#SecondaryMatches#,#PrizeAmount#,'#StartDate#','#EndDate#',NULL)\r\n" + 
			"SET IDENTITY_INSERT [PCHLottoGamePlay].[dbo].[PrizePayouts] OFF";
	
	public static final String getPrizePayoutCount = 
			"SELECT count(*) Id FROM [PCHLottoGamePlay].[dbo].[PrizePayouts]";	
	
	public static final String createPartialCashWinEntry =
			"USE PCHLottoGamePlay\r\n" + 
			"Set Nocount On\r\n" + 
			"DECLARE @RC int\r\n" + 
			"DECLARE @GameId int\r\n" + 
			"DECLARE @DrawingDate datetime\r\n" + 
			"DECLARE @MemberId uniqueidentifier\r\n" + 
			"DECLARE @Picks nvarchar(255)\r\n" + 
			"DECLARE @NumMatches smallint\r\n" + 
			"EXECUTE @RC = [PCHLottoGamePlay].[dbo].[GP_Test_CreatePartialCashWinEntry] \r\n" + 
			"   @GameId = #ContestID#\r\n" + 
			"  ,@DrawingDate = '#DrawingDate# 23:59:59'\r\n" + 
			"  ,@MemberId = '#GMT#'\r\n" + 
			"  ,@Picks = '#PickNumbers#'\r\n" + 
			"  ,@NumMatches = #NumMatches#\r\n" + 
			"Select @RC";
	
	public static final String GETTOPRECENTWINNERS =
			"USE PCHLottoGamePlay\r\n" + 
			"Set Nocount On\r\n" +
			"DECLARE @RC int\r\n" + 
			"DECLARE @Limit tinyint\r\n" + 
			"DECLARE @BusinessUnit nvarchar(255)\r\n" + 
			"DECLARE @MinPrizeAmount decimal(19,5)\r\n" + 
			"DECLARE @WinnerType varchar(10)\r\n" + 
			"EXECUTE @RC = [PCHLottoGamePlay].[dbo].[GP_GetTopRecentWinners] \r\n" + 
			"   @Limit = #EntryLimit#\r\n" + 
			"  ,@BusinessUnit = '#BU#'\r\n" + 
			"  ,@MinPrizeAmount = #MinPrizeAmount#\r\n" + 
			"  ,@WinnerType = '#WinnerType#'\r\n" + 
			"Select @RC";
	
}
