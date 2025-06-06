package handle2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.*;

public class LmsCrawling {
	private String lmsId;
	private String lmsPass;
	private String url;
	public LmsCrawling(String id, String pass) {
		this.lmsId = id;
		this.lmsPass = pass;
		this.url = "https://lms1.knu.ac.kr/";
	}

    public String parseKoreanDateTimeToFormatted(String raw) {
        try {
            if (raw.contains("until")) {
                raw = raw.split("until")[0].trim();
            }

            Pattern timePattern = Pattern.compile("(오전|오후)\\s*\\d{1,2}:\\d{2}");
            Matcher matcher = timePattern.matcher(raw);

            String datePart, timePart = null;

            if (matcher.find()) {
                timePart = matcher.group();
                datePart = raw.substring(0, matcher.start()).trim();
            } else {
                // 시간 없으면 날짜만 존재, null 리턴
                return null;
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE", Locale.KOREAN);
            LocalDate date = LocalDate.parse(datePart, dateFormatter);

            boolean isPM = timePart.contains("오후");
            String timeOnly = timePart.replaceAll("[^0-9:]", "");
            LocalTime time = LocalTime.parse(timeOnly, DateTimeFormatter.ofPattern("H:mm"));

            if (isPM && time.getHour() < 12) {
                time = time.plusHours(12);
            }
            if (!isPM && time.getHour() == 12) {
                time = time.minusHours(12);
            }

            LocalDateTime dateTime = LocalDateTime.of(date, time);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return dateTime.format(outputFormatter).toString();

        } catch (Exception e) {
            return null;
        }
    }
	public ArrayList<Task> crawling(int id){ // id = ArrayList.size()-1;
		ArrayList<Task> taskInfos = null;
        // WebDriverManager를 사용해 크롬 드라이버 설정
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        // 크롬 드라이버 인스턴스 생성
        WebDriver driver = new ChromeDriver(options);
        // 명시적 대기 설정 (최대 20초)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        //lms 접속
        driver.get(url);

        try {
            WebElement loginLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"visual\"]/div/div[2]/div[2]"))
            );
            loginLink.click();

            WebElement goLogin = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"form1\"]/div[2]"))
            );
            goLogin.click();

            WebElement setId = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"idpw_id\"]"))
            );
            setId.sendKeys(lmsId); // ← 실제 아이디로 변경

            WebElement setPass = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"idpw_pw\"]"))
            );	
            setPass.sendKeys(lmsPass); // ← 실제 비밀번호로 변경

            WebElement pushBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"btn-login\"]"))
            );
            pushBtn.click();
            WebElement pushToMySub = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"visual\"]/div/div[2]/div[2]/div[1]/a"))
            );
            pushToMySub.click();
            
            List<WebElement> plannerItems = driver.findElements(By.cssSelector(".PlannerItem-styles__layout"));
            taskInfos = new ArrayList<>();
            for (WebElement item : plannerItems) {
                try {
                    String course = item.findElement(By.cssSelector(".PlannerItem-styles__type span"))
                                        .getText().trim();
                    String content = item.findElement(By.cssSelector(".fdaJD_bGBk"))
                                         .getText().trim();
                    // 과제명, 마감일 추출
                    String title = content;
                    String deadline = null;

                    if (content.contains(", due ")) {
                        String[] parts = content.split(", due ");
                        title = parts[0];
                        deadline = parts[1];
                    } else if (content.contains(", all day on ")) {
                        String[] parts = content.split(", all day on ");
                        title = parts[0];
                        deadline = parts[1];
                    } else if (content.contains(", at ")) {
                        String[] parts = content.split(", at ");
                        title = parts[0];
                        deadline = parts[1];
                    }
                    // 앞에 붙은 "과제", "행사", "퀴즈" 같은 머리말 제거, 공지 사항 제외
                    if(title.contains("공지 사항")) {
                    	continue;
                    }
                    title = title.replaceFirst("^(과제|행사|퀴즈)\\s*", "").trim();
                    id++;
                    taskInfos.add(new Task(id,course,title,parseKoreanDateTimeToFormatted(deadline)));
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            System.out.println("오류 발생: 크롤링 실패, 아이디와 비밀번호를 확인하고, 변경하세요.");
        } finally {
            driver.quit();
        }
        return taskInfos;
	}
}