package nl.lucas.letscookapi.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import nl.lucas.letscookapi.model.Equipment;
import nl.lucas.letscookapi.model.Ingredient;
import nl.lucas.letscookapi.model.Recipe;
import nl.lucas.letscookapi.model.Step;
import org.dom4j.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ExportToPdf {

    private final Recipe recipe;
    Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

    public ExportToPdf(Recipe recipe) {
        this.recipe = recipe;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        setFont(18, Color.BLACK);

        //RECEPT TITEL
        Paragraph recipeTitle = new Paragraph(recipe.getName(), font);
        recipeTitle.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(recipeTitle);

        setFont(12, Color.BLACK);

        //CALORIEËN
        Paragraph calories = new Paragraph("Calorieën: " + recipe.getCalories(), font);
        calories.setSpacingBefore(10);
        document.add(calories);

        //BEREIDINGSDUUR
        Paragraph timeInMinutes = new Paragraph("Bereidingsduur: " + recipe.getTimeInMinutes() + " minuten", font);
        document.add(timeInMinutes);

        //INGREDIËNTEN
        Paragraph ingredientsTile = new Paragraph("Dit zijn de ingrediënten: ", font);
        ingredientsTile.setSpacingBefore(20);
        document.add(ingredientsTile);

        PdfPTable ingredientsTable = setTableStyle(2, 100f, new float[]{8f, 2f}, 10);
        writeIngredientsTable(ingredientsTable);
        document.add(ingredientsTable);

        //BENODIGDHEDEN
        Paragraph equipmentTitle = new Paragraph("Verder heb je nodig: ", font);
        equipmentTitle.setSpacingBefore(20);
        document.add(equipmentTitle);

        PdfPTable equipmentTable = setTableStyle(1, 100f, new float[]{5f}, 10);
        writeEquipmentTable(equipmentTable);
        document.add(equipmentTable);

        //STAPPEN
        Paragraph stepsTitle = new Paragraph("Bereidingswijze: ", font);
        stepsTitle.setSpacingBefore(20);
        document.add(stepsTitle);

        PdfPTable stepsTable = setTableStyle(2, 100f, new float[]{1f, 12f}, 10);
        writeStepsTable(stepsTable);
        document.add(stepsTable);

        document.close();
    }

    private void writeIngredientsTable(PdfPTable table) {
        List<Ingredient> ingredientsList = recipe.getIngredients();
        for (Ingredient ingredient : ingredientsList) {
            PdfPCell cellName = new PdfPCell(new Paragraph(ingredient.getName()));
            PdfPCell cellGrams = new PdfPCell(new Paragraph(String.valueOf(ingredient.getWeightInGrams() + " gram")));
            cellName.setBorderWidth(0f);
            cellGrams.setBorderWidth(0f);
            table.addCell(cellName);
            table.addCell(cellGrams);
        }
    }

    private void writeEquipmentTable(PdfPTable table) {
        List<Equipment> equipmentList = recipe.getEquipment();
        for (Equipment equipment : equipmentList) {
            PdfPCell cellName = new PdfPCell(new Paragraph(equipment.getName()));
            cellName.setBorderWidth(0f);
            table.addCell(cellName);
        }
    }

    private void writeStepsTable(PdfPTable table) {
        List<Step> stepsList = recipe.getSteps();
        for (Step step : stepsList) {
            PdfPCell cellCount = new PdfPCell(new Paragraph(String.valueOf(step.getStepCount())));
            PdfPCell cellDesc = new PdfPCell(new Paragraph(step.getDescription()));
            cellCount.setBorderWidth(0f);
            cellDesc.setBorderWidth(0f);
            table.addCell(cellCount);
            table.addCell(cellDesc);
        }
    }

    private void setFont(int size, Color color) {
        font.setSize(size);
        font.setColor(Color.BLACK);
    }

    private PdfPTable setTableStyle(int numCol, float widthPercentage, float[] cellWidths, int spacing) {
        PdfPTable table = new PdfPTable(numCol);
        table.setWidthPercentage(widthPercentage);
        table.setWidths(cellWidths);
        table.setSpacingBefore(spacing);
        return table;
    }

}
