package jp.co.addon.form;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MyForm {

	private String wariate;

	@Size(max=3)
	private String text;
	private boolean checked;
	private String message;

}
