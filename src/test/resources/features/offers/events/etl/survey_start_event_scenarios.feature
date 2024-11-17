#Author: bmacaluso@pch.com
Feature: Verify different survey start event are posted to the correct log files
  In order to verify survey start events are being logged
  user needs to land on the surveys page and start taking surveys from different vendors

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the correct loi and cpi values are present in Lucid start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP'
    And I pause execution for 1 seconds
    Then I verify 'Lucid' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    15 |
      | MinimumGrossCPI |    .5 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile @Sanity
  Scenario Outline: Verify start event is recorded in log and the correct DEFAULT loi and cpi values are present in Lucid start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_Lucid_Tokens_NoBP_Default'
    And I pause execution for 1 seconds
    Then I verify 'Lucid' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    10 |
      | MinimumGrossCPI |  0.01 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the loi and cpi values are present in LucidApi start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_VinLucidApiTokens'
    And I answer all basic profile questions
    And I pause execution for 1 seconds
    Then I verify 'LucidApi' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    30 |
      | MinimumGrossCPI |   0.1 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the DEFAULT loi and cpi values are present in LucidApi start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_lucidApiDefault'
    And I answer all basic profile questions
    And I pause execution for 2 seconds
    Then I verify 'LucidApi' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |     1 |
      | MinimumGrossCPI |  0.05 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the loi and cpi values are present in PureSpectrum start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_PSplacement'
    And I close the Take a Tour popup
    And I answer all basic profile questions
    And I pause execution for 1 seconds
    Then I verify 'PURESPECTRUM' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    20 |
      | MinimumGrossCPI |  0.20 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the DEFAULT loi and cpi values are present in PureSpectrum start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I navigates to survey 'Auto_psdefault'
    And I close the Take a Tour popup
    And I answer all basic profile questions
    And I pause execution for 1 seconds
    Then I verify 'PURESPECTRUM' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    10 |
      | MinimumGrossCPI |     0 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the loi and cpi values are present in Qmee start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_VinQmeeTokens'
    And I answer all basic profile questions
    And I pause execution for 1 seconds
    Then I verify 'Qmee' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |     1 |
      | MinimumGrossCPI |     1 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the DEFAULT loi and cpi values are present in Qmee start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_qmeedefault'
    And I answer all basic profile questions
    And I pause execution for 1 seconds
    Then I verify 'Qmee' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |     1 |
      | MinimumGrossCPI |     1 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the loi and cpi values are present in GRL start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_GRL'
    And I answer all basic profile questions
    And I pause execution for 1 seconds
    Then I verify 'GRL' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    30 |
      | MinimumGrossCPI |     1 |

    Examples: 
      | userType |
      | full reg |

  @Desktop @Mobile
  Scenario Outline: Verify start event is recorded in log and the DEFAULT loi and cpi values are present in GRL start event json for "<userType>" user
    Given I land on the Surveys Page as a "<userType>" user
    And I close the Take a Tour popup
    And I navigates to survey 'Auto_grldefault'
    And I answer all basic profile questions
    And I pause execution for 1 seconds
    Then I verify 'GRL' survey start event json contains the following elements
      | Element         | Value |
      | LengthInMinutes |    10 |
      | MinimumGrossCPI |     0 |

    Examples: 
      | userType |
      | full reg |
