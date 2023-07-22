# 로또
## 진행 방법
* 로또 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)


# 1단계 -  문자열 계산기 요구사항
- [x] 사용자로부터 식을 입력받는다
- [x] 콘솔에 결과를 출력한다
- ExpressionParser
  - [x] 주어진 문자열을 공백 단위로 파싱한다
  - [x] 숫자와 사칙연산자 그리고 공백을 제외한 다른 글자가 존재하는 지 검증한다
  - [x] 숫자 - 사칙연산자 - 숫자의 패턴인 문자열인지 검증한다

- 변환기
  - [x] 문자열을 숫자로 변환한다
    - [x] 정수인 숫자 임을 검증한다
- 계산기
  - [x] 나눗셈 결과는 정수로 떨어진다
  - [x] 덧셈을 수행한다
  - [x] 뺄셈을 수행한다
  - [x] 곱셈을 수행한다
  - [x] 입력값의 순서에 따라 결정된 순서로 사칙연산을 수행한다( 사칙연산 우선순위를 무시한다 )

Calculator 의 indent 를 줄이기 위해 객체 추가
- 표현식 
  - [x] 연산을 위해선 "연산자" 그리고 "다음 수" 가 필요하다 -> 다음에 존재하는 "연산자" 가 존재하는지를 알려주면 된다
  - [x] 피연산자 개수 = 연산자 개수 + 1 이다
    - [x] 피연산자 추출 시 검증 : 피연산자 개수 = 연산자 개수 + 1 
    - [x] 연산자 추출 시 검증 : 연산자 개수 = 피연산자 개수 - 1

- ConsoleView
  - [x] 사용자로부터 입력을 받는다
  - [x] 결과만을 출력한다

## 프로그래밍 요구사항
- indent 의 depth 를 1단계로 줄일 것 (while + if -> 2단계)
- 메서드 크기가 최대 10라인을 넘지 않을 것 
- else 사용하지 말 것

## AssertJ 힌트
- 자주 발생하는 Exception 의 경우 별도의 Exception 메서드를 제공하고 있다. 
  - IllegalArgument
  - IllegalState
  - IO
  - NPE
- 앞에서 사용했던 ParameterizedTest 와 ValueSource 사용 고려

# 2단계 : 로또(자동) + 🚀 3단계 - 로또(2등)
## 힌트 
- Collections.shuffle() 활용해 로또 자동 생성
- Collections.sort() 활용해 정렬
- ArrayList 의 contains() 사용해 값 존재 유무 확인 
- 내가 기존에 작성했던 Winning 이 Rank 와 유사한 것 같다. 그런데 여기에 보너스 볼 포함하여 5개가 일치된 경우에 대한 당첨금에 대한 정보도 갖는다.


## 프로그래밍 요구사항
- 모든 기능은 TDD 로 구현해 단위 테스트가 존재해야 한다
  - 핵심로직 코드와 UI 담당 로직 분리 
- Java enum 을 적용해 프로그래밍을 구현한다
- 일급 컬렉션을 쓴다
- Indent 는 1까지만 허용한다 
- 함수 길이가 15를 넘지 않도록 한다. 
- else 예약어 사용하지 않기 

## 요구사항 작성 

- Main

- 로또숫자(LottoNumber)
  - 1 ~ 45 사이의 숫자임을 알고 있다
  
- 로또 머신
  - 초기 금액을 알고 있다. 
  - 로또 하나의 값이 1000원임을 알고 있다
  - 수동 구매 개수를 알고 있다.
  - 보관하는 로또에 대해 수동, 자동 여부를 구분할 필요는 없다.
  - [x] 돈을 받으면 자동으로 로또들을 생성하고 있는다.
  - [x] 수동 로또 개수를 입력 받으면 
    - [x] -> 유효성을 검증한다 : 금액/1000 >= 수동 로또 개수
    - [x] -> 자동 로또 개수를 계산한다 
    - [x] -> 자동 로또들을 생성한다
  - [ ] 수동 로또 번호를 입력 받는다
    - [ ] (LottoMachine 밖 ) 파싱 가능한 형태의 입력인지 검증
  - [ ] 수동 로또 를 하나씩 추가 할 수 있다
    - [ ] 로또를 생성 한다
    - [ ] 로또 지갑에 추가한다
  - [ ] 사용자 입력에 대한 예외를 처리한다
    - [ ] 금액
    - [ ] 수동 로또 개수
    - [ ] 수동 로또 번호
    - [ ] 당첨번호
  - [x] 당첨 넘버와 보너스 넘버를 받아, 매칭 개수별 로또 개수를 통계 정보를 알려줄 수 있다.
  - [x] 구입금액 대비 당첨금액을 통해 수익률을 알려준다
  - [x] 로또 개수를 알려준다

- 로또 생성기
  - 로또는 1 ~ 45 사이의 숫자로 구성됨을 알고 있다
  - 로또는 6개의 숫자로 구성됨을 알고 있다
  - [x] 1 ~ 45 의 숫자 6개로 로 이루어진 List 를 리턴한다
  - [x] 로또 하나를 자동 생성한다
  - [x] 개수가 주어지면 개수에 해당하는 개수만큼의 로또들을 자동 생성한다
  - [x] 수동 로또 번호들이 주어지면 로또를 생성한다
    - [x] 주어진 수동 로또 번호가 유효한 숫자인지 검증한다
- 로또
  - 자신을 구성하는 숫자 6개를 알고 있다 
  - 로또는 6개의 중복되지 않는 로또넘버로 구성됨을 알고 있다
  - [x] 당첨 번호와 비교해 일치하는 숫자의 개수를 알려준다 
  - [x] 보너스 넘버를 갖고 있는지 여부를 알려준다
  - [x] 중복되지 않은 6 개의 숫자로 이루어진다는 불변식을 지킨다

- 로또 지갑
  - 생성된 로또 들에 대해 알고 있다. 
  - [x] 지갑에 로또를 추가한다
  - [x] 원하는 시점에 지갑에 있는 로또들에 대한 총 당첨금액을 알려준다
  - [x] n 개 일치하는 로또의 개수를 알려준다

- 당첨금(Winning) -> 당첨과 관련된 정보들을 알고 있다
  - 일치하는 숫자의 개수 별 당첨금을 알고 있다.
  - [x] 일치하는 숫자의 개수 별 당첨금을 알려준다
  - [x] 당첨금을 받을 수 있는 최소 일치 개수를 알려준다
  - [x] 당첨번호와 5개 일치하는(matchCount 가 5개 라는) 것 외에, 보너스 볼을 갖고 있는 경우 (2등 당첨자)의 당첨금액을 알려준다

- 문자열 파서
  - [x] 문자열을 ', ' 단위로 파싱하여 문자열 리스트로 리턴한다

- ConsoleView
  - 문자열 출력
  - 입력을 받아 리턴
  - 입력을 받아야 하는 경우, 입력을 요 구하는 출력문은 동일한 메서드에서 실행하도록 하자(클라이언트가 public 메서드를 호출하는 순서를 우리가 보장할 수 없으니)

  

