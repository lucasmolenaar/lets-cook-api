package nl.lucas.letscookapi.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
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

public class RecipePdfExporter {

    private final Recipe recipe;

    public RecipePdfExporter(Recipe recipe) {
        this.recipe = recipe;
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
//            table.addCell(ingredient.getName());
//            table.addCell(String.valueOf(ingredient.getWeightInGrams() + " gram"));
        }
    }

    private void writeEquipmentTable(PdfPTable table) {
        List<Equipment> equipmentList = recipe.getEquipment();
        for (Equipment equipment : equipmentList) {
            PdfPCell cellName = new PdfPCell(new Paragraph(equipment.getName()));
            cellName.setBorderWidth(0f);
            table.addCell(cellName);
//            table.addCell(equipment.getName());
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
//            table.addCell(String.valueOf(step.getStepCount()));
//            table.addCell(step.getDescription());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        //RECEPT TITEL
        Paragraph recipeTitle = new Paragraph(recipe.getName(), font);
        recipeTitle.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(recipeTitle);

        font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(12);
        font.setColor(Color.BLACK);

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

        PdfPTable ingredientsTable = new PdfPTable(2);
        ingredientsTable.setWidthPercentage(100f);
        ingredientsTable.setWidths(new float[]{8f, 2f});
        ingredientsTable.setSpacingBefore(10);
        writeIngredientsTable(ingredientsTable);
        document.add(ingredientsTable);

        //BENODIGDHEDEN
        Paragraph equipmentTitle = new Paragraph("Dit zijn de benodigdheden: ", font);
        equipmentTitle.setSpacingBefore(20);
        document.add(equipmentTitle);

        PdfPTable equipmentTable = new PdfPTable(1);
        equipmentTable.setWidthPercentage(100f);
        equipmentTable.setWidths(new float[]{5f});
        equipmentTable.setSpacingBefore(10);
        writeEquipmentTable(equipmentTable);
        document.add(equipmentTable);

        //STAPPEN
        Paragraph stepsTitle = new Paragraph("Dit zijn de stappen: ", font);
        stepsTitle.setSpacingBefore(20);
        document.add(stepsTitle);

        PdfPTable stepsTable = new PdfPTable(2);
        stepsTable.setWidthPercentage(100f);
        stepsTable.setWidths(new float[]{1f, 12f});
        stepsTable.setSpacingBefore(10);
        writeStepsTable(stepsTable);
        document.add(stepsTable);

////         FOTO TOEVOEGEN (NOG TE GROOT, MOET KLEINER)
//        document.add(new Image(Image.getInstance(recipe.getRecipePicture())) {
//            @Override
//            public byte[] getRawData() {
//                return super.getRawData();
//            }
//        });

        document.close();
    }

}
