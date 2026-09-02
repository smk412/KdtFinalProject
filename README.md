# WEPLE

> 프로젝트와 업무를 한곳에서 관리하는 협업 플랫폼  
> 팀 프로젝트에서 마일스톤·로드맵·간트 차트·대시보드 개발과 조회 성능 개선을 담당했습니다.

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-007396?style=flat-square&logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=flat-square&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/MyBatis-3.0-B32629?style=flat-square">
  <img src="https://img.shields.io/badge/Oracle-Database-F80000?style=flat-square&logo=oracle&logoColor=white">
  <img src="https://img.shields.io/badge/Thymeleaf-Template-005F0F?style=flat-square&logo=thymeleaf&logoColor=white">
  <img src="https://img.shields.io/badge/Docker-Container-2496ED?style=flat-square&logo=docker&logoColor=white">
</p>

---

## 프로젝트 소개

WEPLE은 기업과 개발 조직에서 프로젝트, 일감, 마일스톤, 일정 및 협업 이력을 통합 관리할 수 있도록 개발한 웹 기반 협업 플랫폼입니다.

프로젝트별 업무 현황을 대시보드에서 확인하고, 마일스톤과 간트 차트를 통해 전체 개발 일정을 관리할 수 있습니다. 역할 및 권한에 따라 접근 가능한 기능을 구분하고, GitHub 저장소·파일·위키·알림 기능을 함께 제공하도록 구성했습니다.

- 개발 기간: 2026.06 ~ 2026.07
- 개발 인원: 5명
- 개발 형태: 팀 프로젝트
- 원본 저장소: [KDTFinalProject/KdtFinalProject](https://github.com/KDTFinalProject/KdtFinalProject)
- 개인 GitHub: [github.com/smk412](https://github.com/smk412)

> 이 저장소는 팀 프로젝트를 개인 포트폴리오 목적으로 포크한 저장소입니다.  
> 아래 내용은 전체 프로젝트 소개와 함께 제가 담당한 기능 및 기술적 개선 사항을 중심으로 정리했습니다.

---

## 주요 기능

### 프로젝트 및 업무 관리

- 프로젝트 생성 및 구성원 관리
- 일감 등록·수정·삭제 및 담당자 지정
- 일감 상태·우선순위·유형별 관리
- 칸반 보드와 프로젝트 진행 현황 제공

### 마일스톤 및 로드맵

- 상위 버전과 하위 마일스톤 계층 관리
- 마일스톤별 연결 일감 관리
- 완료 일감 수와 평균 진척도 계산
- 마감일을 기준으로 지연 일수 표시
- 프로젝트 단위 로드맵 조회

### 일정 및 현황 시각화

- 간트 차트를 이용한 프로젝트 일정 확인
- 프로젝트 개요 및 전체 진행률 제공
- 대시보드에서 참여 프로젝트와 최근 활동 조회
- 마감 임박·지연 일감 추적

### 협업 지원

- GitHub 저장소 및 커밋 정보 조회
- 프로젝트 위키
- 파일 업로드 및 관리
- 알림 및 업무 이력 조회
- 사용자·그룹·역할·권한 관리

---

# 담당 역할

## 송민규 — Backend / Full Stack

프로젝트에서 Git 협업 관리와 함께 다음 기능을 주로 담당했습니다.

| 담당 영역 | 주요 내용 |
|---|---|
| 마일스톤 | 등록·조회·수정·삭제, 상위 버전과 하위 마일스톤 계층 관리 |
| 로드맵 | 프로젝트별 마일스톤·일감 통계 조회 및 화면 구성 |
| 조회 최적화 | 복잡한 계층형 SQL 분리, 복합 인덱스 적용, Java Map 기반 트리 조립 |
| 간트 차트 | 프로젝트 일정과 일감 데이터를 간트 차트 구조로 변환 |
| 프로젝트 개요 | 프로젝트 진행률, 구성원 및 일감 현황 조회 |
| 대시보드 | 참여 프로젝트, 최근 활동, 마감 일감 조회 |
| 일감 유형 | 일감 유형 등록·조회·수정 및 관리 UI 구현 |
| 권한 처리 | 프로젝트 구성원 및 권한 코드 기반 접근 제어 |
| 통합 테스트 | 삭제 일감 제외, 권한 및 화면 결함 수정 |

### 기여 범위

```text
Milestone
├─ 상위 버전 등록
├─ 하위 마일스톤 CRUD
├─ 마일스톤별 일감 연결
├─ 상태·진척도·지연일 계산
├─ 로드맵 계층 조회
└─ 프로젝트 권한 검증

Project Visualization
├─ 프로젝트 개요
├─ 간트 차트
└─ 대시보드

System
├─ 일감 유형 관리
└─ 역할·권한 기반 접근 제어
```

---

# 핵심 개선 사례

## 마일스톤 로드맵 조회 최적화

### 문제 상황

기존 로드맵 조회는 하나의 SQL 안에서 다음 작업을 모두 처리했습니다.

- 부모 마일스톤 조회
- 자식 마일스톤 self join
- 마일스톤별 일감 통계 집계
- 부모 버전의 통계 재집계
- 부모·자식 계층 결과 생성
- 지연일 계산

```sql
FROM milestone parent
LEFT JOIN (
    -- 자식 마일스톤 및 일감 통계 집계
) parent_stats
    ON parent.milestone_id = parent_stats.parent_milestone_id
LEFT JOIN milestone child
    ON parent.milestone_id = child.parent_milestone_id
LEFT JOIN (
    -- 자식 마일스톤별 일감 통계 집계
) task_stats
    ON child.milestone_id = task_stats.milestone_id
WHERE parent.project_id = #{projectId}
  AND parent.parent_milestone_id IS NULL
```

이 구조에는 다음 문제가 있었습니다.

1. `milestone`과 `task` 테이블을 여러 번 조회했습니다.
2. 부모 마일스톤 정보가 자식 수만큼 중복되어 반환됐습니다.
3. self join과 중첩 집계 때문에 실행 계획이 복잡해졌습니다.
4. DB가 조회뿐 아니라 계층 구조 생성과 통계 계산까지 담당했습니다.
5. 데이터가 증가할수록 DB 연산과 MyBatis 결과 매핑 부담이 커지는 구조였습니다.

### 1. 조회 조건에 맞춘 복합 인덱스 구성

로드맵은 항상 특정 프로젝트 안에서 최상위 버전과 하위 마일스톤을 조회합니다.

```sql
WHERE project_id = :projectId
  AND parent_milestone_id IS NULL
```

이 조회 패턴에 맞춰 복합 인덱스를 구성했습니다.

```sql
CREATE INDEX IDX_MILESTONE_ROADMAP
ON MILESTONE (
    PROJECT_ID,
    PARENT_MILESTONE_ID
);
```

이를 통해 전체 마일스톤을 탐색하기 전에 프로젝트 범위를 먼저 좁히고, 해당 프로젝트 안에서 부모 또는 자식 마일스톤을 조회할 수 있도록 했습니다.

### 2. 복잡한 SQL을 평면 조회 두 개로 분리

#### 프로젝트 마일스톤 조회

```xml
<select id="selectFlatMilestones" resultType="MilestoneVO">
    SELECT
        milestone_id         AS milestoneId,
        project_id           AS projectId,
        user_code            AS userCode,
        milestone_title      AS milestoneTitle,
        milestone_describe   AS milestoneDescribe,
        finish_date          AS finishDate,
        milestone_status     AS milestoneStatus,
        created_at           AS createdAt,
        updated_at           AS updatedAt,
        parent_milestone_id  AS parentMilestoneId
    FROM milestone
    WHERE project_id = #{projectId}
</select>
```

부모·자식 self join을 제거하고 마일스톤마다 한 행만 반환하도록 변경했습니다.

#### 마일스톤별 일감 통계 조회

```xml
<select id="selectTaskStatsFlat" resultType="TaskStatDTO">
    SELECT
        milestone_id AS milestoneId,
        COUNT(*) AS totalTaskCount,
        COUNT(
            CASE WHEN task_progress = 100 THEN 1 END
        ) AS closedTaskCount,
        ROUND(
            AVG(NVL(task_progress, 0))
        ) AS progressPercentage
    FROM task
    WHERE project_id = #{projectId}
      AND milestone_id IS NOT NULL
      AND deleted_yn = 'N'
    GROUP BY milestone_id
</select>
```

프로젝트 범위를 먼저 제한하고, 삭제되지 않은 일감만 마일스톤별로 한 번씩 집계했습니다.

### 3. 통계 결과를 Map으로 변환

```java
List<MilestoneVO> flatMilestones =
        milestoneMapper.selectFlatMilestones(projectId);

List<TaskStatDTO> taskStats =
        milestoneMapper.selectTaskStatsFlat(projectId);

Map<Long, TaskStatDTO> statMap = taskStats.stream()
        .collect(Collectors.toMap(
                TaskStatDTO::getMilestoneId,
                stat -> stat
        ));
```

통계 결과를 `milestoneId` 기준 Map으로 변환하여 각 마일스톤의 통계를 반복 탐색하지 않고 연결할 수 있도록 했습니다.

```java
TaskStatDTO stat = statMap.get(milestone.getMilestoneId());

if (stat != null) {
    info.setTotalTaskCount(stat.getTotalTaskCount());
    info.setClosedTaskCount(stat.getClosedTaskCount());
    info.setProgressPercentage(stat.getProgressPercentage());
}
```

### 4. Java에서 계층 구조 조립

```java
Map<Long, MilestoneInfoVO> milestoneMap = infoList.stream()
        .collect(Collectors.toMap(
                MilestoneInfoVO::getMilestoneId,
                milestone -> milestone
        ));

List<MilestoneInfoVO> versions = new ArrayList<>();

for (MilestoneInfoVO milestone : infoList) {
    if (milestone.getParentMilestoneId() == null) {
        versions.add(milestone);
        continue;
    }

    MilestoneInfoVO parent = milestoneMap.get(
            milestone.getParentMilestoneId().longValue()
    );

    if (parent != null) {
        parent.getChildMilestones().add(milestone);
    }
}
```

DB에서 중복된 계층형 결과를 생성하는 대신, `milestoneId`와 `parentMilestoneId`를 이용해 서비스 계층에서 트리를 구성했습니다.

Map을 사용해 부모를 바로 찾기 때문에 전체 조립 과정은 마일스톤 수를 N이라고 했을 때 O(N)에 가까운 구조입니다.

### 5. 부모 버전 통계 계산

```java
for (MilestoneInfoVO parent : versions) {
    List<MilestoneInfoVO> children =
            parent.getChildMilestones();

    if (children.isEmpty()) {
        continue;
    }

    parent.setTotalMilestoneCount(children.size());

    long closedCount = children.stream()
            .filter(child ->
                    "g2".equals(child.getMilestoneStatus()))
            .count();

    parent.setClosedMilestoneCount((int) closedCount);

    double averageProgress = children.stream()
            .mapToInt(
                    MilestoneInfoVO::getProgressPercentage
            )
            .average()
            .orElse(0.0);

    parent.setProgressPercentage(
            (int) Math.round(averageProgress)
    );
}
```

기존 SQL 인라인 뷰에서 수행하던 부모 통계 계산을 서비스 계층으로 이동했습니다. 이를 통해 조회 SQL을 단순화하고, 진척도 계산 규칙을 애플리케이션 코드에서 명확히 확인할 수 있게 했습니다.

### 개선 결과

| 구분 | 개선 전 | 개선 후 |
|---|---|---|
| SQL 구조 | self join과 중첩 집계가 포함된 단일 SQL | 마일스톤 조회와 일감 통계 조회로 분리 |
| 반환 데이터 | 부모 정보가 자식 수만큼 중복 | 마일스톤당 한 행 |
| 계층 구성 | DB join 및 MyBatis collection | Java Map 기반 조립 |
| 부모 통계 | SQL 인라인 뷰에서 계산 | 서비스 계층에서 계산 |
| 데이터 범위 | 실행 계획에 따라 광범위한 탐색 가능 | `project_id` 기준으로 우선 제한 |
| 유지보수 | 조회와 비즈니스 계산이 혼재 | 조회와 계산 책임 분리 |

> 단순히 인덱스만 추가한 것이 아니라, DB가 계층 조회와 통계 계산을 모두 담당하던 구조를 단순 조회와 애플리케이션 조립으로 분리했습니다.

---

## 삭제 일감의 통계 반영 문제 해결

### 문제

일감을 논리 삭제해도 기존 통계 쿼리가 삭제된 일감을 포함해 집계하는 문제가 있었습니다.

그 결과 화면에서는 삭제된 일감이 보이지 않지만 다음 값에는 계속 포함될 수 있었습니다.

- 전체 일감 수
- 완료 일감 수
- 평균 진척도
- 간트 차트 및 프로젝트 개요 통계

### 해결

일감 조회와 통계 쿼리에 논리 삭제 조건을 일관되게 추가했습니다.

```sql
AND deleted_yn = 'N'
```

```sql
SELECT
    milestone_id,
    COUNT(*) AS total_task_count,
    ROUND(AVG(NVL(task_progress, 0))) AS progress_percentage
FROM task
WHERE project_id = #{projectId}
  AND milestone_id IS NOT NULL
  AND deleted_yn = 'N'
GROUP BY milestone_id
```

이를 통해 실제 화면에 노출되는 일감과 통계에 사용되는 일감의 범위를 일치시켰습니다.

---

## 프로젝트 접근 권한 처리

프로젝트 기능에 접근할 때 단순 로그인 여부만 확인하지 않고 다음 조건을 함께 검증하도록 구현했습니다.

- 해당 프로젝트 구성원인지 확인
- 사용자에게 부여된 역할 조회
- 역할에 연결된 권한 코드 확인
- 조회·등록·수정·삭제 작업별 접근 허용

```java
boolean projectMember =
        milestoneService.checkProjectMembership(
                projectId,
                loginUser.getUserCode()
        );

if (!projectMember) {
    return "redirect:/access-denied";
}
```

```sql
SELECT COUNT(*)
FROM members
WHERE project_id = #{projectId}
  AND user_code = #{userCode}
```

마일스톤, 간트 차트, 프로젝트 개요 등 프로젝트에 종속된 화면에 동일한 접근 검증 방식을 적용해 URL 직접 접근도 제한했습니다.

---

## 간트 차트 및 프로젝트 개요

프로젝트에 등록된 일감과 마일스톤 정보를 조회한 뒤 화면에서 사용할 수 있는 계층형 데이터로 변환했습니다.

주요 처리 내용은 다음과 같습니다.

- 프로젝트별 일감 조회
- 상위·하위 일감 관계 구성
- 시작일과 종료일을 기준으로 간트 차트 데이터 생성
- 삭제된 일감 제외
- 프로젝트 구성원 및 그룹별 현황 조회
- 프로젝트 전체 진행률 계산
- 조회 결과를 DTO로 분리해 화면 전달 구조 단순화

---

## 대시보드

로그인한 사용자를 기준으로 다음 정보를 한 화면에서 확인할 수 있도록 구현했습니다.

- 참여 중인 프로젝트
- 프로젝트별 일감 현황
- 최근 활동
- 마감 예정 및 지연 일감
- 사용자 프로필
- 프로젝트 진행 상태

대시보드 조회에서도 전체 데이터를 가져온 뒤 필터링하지 않고 사용자 및 프로젝트 조건을 SQL에 적용해 필요한 데이터만 조회하도록 구성했습니다.

---

## 기술 스택

### Backend

- Java 21
- Spring Boot 3.5
- Spring MVC
- Spring Security
- MyBatis
- Spring Data JPA
- Maven
- Lombok

### Frontend

- Thymeleaf
- HTML5
- CSS3
- JavaScript
- Bootstrap

### Database

- Oracle Database
- Oracle JDBC
- MyBatis XML Mapper

### Infrastructure and Collaboration

- Docker
- AWS S3
- GitHub
- Jenkins
- Git Flow 기반 브랜치 협업

---

## 애플리케이션 구조

```text
src/main/java/com/weple/cloud
├─ Configuration
├─ auth
├─ dashboard
├─ gantt
├─ milestone
│  ├─ mapper
│  ├─ service
│  │  └─ impl
│  └─ web
├─ outline
├─ project
├─ repository
├─ system
├─ task
└─ wiki

src/main/resources
├─ mapper
│  ├─ dashboard
│  ├─ gantt
│  ├─ milestone
│  ├─ project
│  └─ task
├─ static
├─ templates
└─ application.properties
```

기능별로 Controller, Service, Mapper 계층을 분리하고 MyBatis XML Mapper에서 SQL을 관리했습니다.

---

## 실행 환경

| 구분 | 버전 및 구성 |
|---|---|
| JDK | 21 |
| Spring Boot | 3.5.16 |
| Maven | 3.9.x |
| Database | Oracle |
| WAS | Embedded Tomcat |
| 기본 포트 | 8073 |
| 운영 포트 | 80 |

### 로컬 실행

Oracle DB와 필수 환경변수를 준비한 후 실행합니다.

```bash
mvn spring-boot:run
```

또는 JAR로 패키징해 실행할 수 있습니다.

```bash
mvn clean package
java -jar target/weple-0.0.1-SNAPSHOT.jar
```

> 데이터베이스 및 외부 서비스 인증정보는 보안상 저장소에 공개하지 않습니다.

---

## 팀 구성

| 이름 | 역할 |
|---|---|
| 방진영 | 팀장 / 프로젝트 총괄 |
| 김병완 | 부팀장 / 배포 |
| 김은지 | DB |
| 김민지 | 개발환경 |
| **송민규** | **Git 협업 관리 / 마일스톤·로드맵·간트 차트·대시보드 개발** |

팀원 전체의 협업으로 완성한 프로젝트이며, 이 README는 그중 송민규의 담당 기능과 기술적 개선 경험을 중심으로 정리했습니다.

---

## 회고

WEPLE을 개발하면서 단순히 기능이 동작하는 것과 데이터가 증가해도 안정적으로 동작하는 것은 다른 문제라는 점을 경험했습니다.

특히 마일스톤 로드맵 조회에서는 복잡한 SQL 하나로 모든 문제를 해결하려 했지만, self join과 중첩 집계로 인해 조회 구조가 복잡해졌습니다. 이를 해결하는 과정에서 다음 내용을 학습했습니다.

- 조회 조건과 컬럼 순서를 고려한 복합 인덱스 설계
- SQL 집계 범위를 줄이기 위한 조건절 배치
- DB와 애플리케이션 사이의 책임 분리
- Map을 이용한 선형 시간 계층 구조 구성
- 논리 삭제 데이터와 통계 정합성 관리
- 프로젝트 구성원 및 역할 기반 접근 제어
- 통합 테스트 과정에서 발생한 데이터·화면 결함 수정

이 경험을 통해 SQL을 단순히 작성하는 것을 넘어, 실행 과정과 반환 데이터 구조까지 고려해 설계하는 것이 중요하다는 점을 배웠습니다.
