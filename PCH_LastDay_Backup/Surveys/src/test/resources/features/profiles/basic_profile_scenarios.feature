#Author: bmacaluso@pch.com
@Desktop @Mobile @Regression @RT-07379 @RT-07996
Feature: Build a new basic profile
  In order to build a basic profile
  as a user I need to answer all basic profile questions

  Scenario Outline: On desktop device build a basic profile for "<userType>" user verify correct questions and answers are recorded in DB
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions
    Then I verify basic profile questions and answers exist in Redis Database
      | Question | Answer |
      | 32a      | 32a_13 |
      | 7a       | 7a_07  |
      | 6a       | 6a_03  |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |

  Scenario Outline: On mobile device build a basic profile for "<userType>" user verify correct questions and answers are recorded in DB
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens'
    When I answer all basic profile questions
    Then I verify basic profile questions and answers exist in Redis Database
      | Question | Answer |
      | 32a      | 32a_13 |
      | 7a       | 7a_07  |
      | 6a       | 6a_03  |

    @Sanity
    Examples: 
      | userType |
      | full reg |

    Examples: 
      | userType |
      | guest    |
      | mini reg |
      | silver   |
