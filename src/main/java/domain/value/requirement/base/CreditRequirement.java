package domain.value.requirement.base;

import domain.entity.Course;
import domain.entity.LearningRecord;
import domain.factory.CourseFactory;
import domain.value.matchResult.base.CreditMatchResult;
import domain.value.matchResult.base.MatchEntry;

import java.util.*;

public abstract class CreditRequirement extends AbstractRequirement implements Requirement {
    private final List<String> courseIds;
    private final int totalCredit;

    public CreditRequirement(List<String> courseIds, int totalCredit) {
        this.courseIds = courseIds;
        this.totalCredit = totalCredit;
    }

    @Override
    public CreditMatchResult matchRequirement(final List<LearningRecord> learningRecordList,
                                              final List<Requirement> relatedRequirements,
                                              final Map<String, String> courseExchangeMap) {
        // 待匹配的课程
        Map<String, Course> courseMap = CourseFactory.getCourseMapFromIds(courseIds);
        // 匹配结果
        List<MatchEntry> matchEntries = getMatchedEntries(new HashMap<>(courseMap),
                learningRecordList, courseExchangeMap);
        // 计算成功匹配的学分
        int matchCredit = 0;
        for (MatchEntry matchEntry : matchEntries) {
            matchCredit += matchEntry.getRequireCourse().getCredit();
        }

        // 生成匹配结果
        double matchProgress;
        if (totalCredit == 0 ) {
            matchProgress = 1;
        } else {
            matchProgress = (double) matchCredit / totalCredit;
        }
        if (matchProgress > 1) matchProgress = 1;

        return new CreditMatchResult(matchCredit >= totalCredit, matchEntries,
                matchCredit, totalCredit, matchProgress);
    }
}