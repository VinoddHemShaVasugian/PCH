#Author: bmacaluso@pch.com
@Regression @GaTags @RT-07379
Feature: Build a new basic profile
  In order to build a basic profile
  as a user I need to answer all basic profile questions

  @Desktop
  Scenario Outline: On desktop device Build a basic profile for "<userType>" user verify correct count in DB and GA tag
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions
    Then Verify All GA Tags
      | EventCategory                  | EventAction     | EventLabel     |
      | surveytab/basicprofile/desktop | profilecomplete | survey/profile |
      | surveytab/desktop              | impression      |                |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  @Mobile
  Scenario Outline: On mobile device Build a basic profile for "<userType>" user verify correct count in DB and GA tag
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions
    Then Verify All GA Tags
      | EventCategory                 | EventAction     | EventLabel     |
      | surveytab/basicprofile/mobile | profilecomplete | survey/profile |
      | surveytab/mobile              | impression      |                |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |
