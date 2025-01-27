package domain.value.requirement.impl;

import domain.entity.LearningRecord;
import domain.value.matchResult.base.CreditMatchResult;
import domain.value.requirement.base.*;
import domain.value.matchResult.impl.MajorElectiveMatchResult;

import java.util.List;
import java.util.Map;

/**
 * 专业选修要求
 * 继承【学分要求】
 */
public class MajorElectiveRequirement extends CreditRequirement implements Requirement {

    public MajorElectiveRequirement(List<String> courseIds, int totalCredit) {
        super(courseIds, totalCredit);
    }

    @Override
    public MajorElectiveMatchResult matchRequirement(final List<LearningRecord> learningRecordList,
                                                     final List<Requirement> relatedRequirements,
                                                     final Map<String, String> courseExchangeMap) {
        CreditMatchResult matchResult = super.matchRequirement(learningRecordList,
                relatedRequirements, courseExchangeMap);
        return new MajorElectiveMatchResult(matchResult.isMatched(),
                matchResult.getMatchEntries(), matchResult.getMatchCredit(),
                matchResult.getTotalCredit(), matchResult.getMatchProgress());
    }
}