package com.example.DoAnJava.importer;

import com.example.DoAnJava.Repository.ExamQuestionRepository;
import com.example.DoAnJava.Repository.ExamTestRepository;
import com.example.DoAnJava.models.ExamQuestion;
import com.example.DoAnJava.models.ExamTest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ExamQuestionImporter {

    @Autowired
    private ExamQuestionRepository examQuestionRepository;

    @Autowired
    private ExamTestRepository examTestRepository;

    public void importExcelFile(String filePath, Integer examTestId) {
        try (InputStream excelFile = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);

            // Retrieve the ExamTest entity
            ExamTest examTest = examTestRepository.findById(examTestId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid examTestId: " + examTestId));

            for (Row row : sheet) {
                // Skip header row
                if (row.getRowNum() == 0) {
                    continue;
                }

                ExamQuestion examQuestion = new ExamQuestion();

                examQuestion.setNumber(getNumericCellValue(row.getCell(0)));
                examQuestion.setImageUrl(getStringCellValue(row.getCell(1)));
                examQuestion.setAudioUrl(getStringCellValue(row.getCell(2)));
                examQuestion.setParagraph(getStringCellValue(row.getCell(3)));
                examQuestion.setQuestion(getStringCellValue(row.getCell(4)));
                examQuestion.setOptionA(getStringCellValue(row.getCell(5)));
                examQuestion.setOptionB(getStringCellValue(row.getCell(6)));
                examQuestion.setOptionC(getStringCellValue(row.getCell(7)));
                examQuestion.setOptionD(getStringCellValue(row.getCell(8)));
                examQuestion.setCorrectAnswer(getStringCellValue(row.getCell(9)));
                examQuestion.setPart(getNumericCellValue(row.getCell(10)));
                examQuestion.setScript(getStringCellValue(row.getCell(11)));

                // Set the ExamTest entity
                examQuestion.setExamTest(examTest);

                examQuestionRepository.save(examQuestion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Integer getNumericCellValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return null;
    }
}