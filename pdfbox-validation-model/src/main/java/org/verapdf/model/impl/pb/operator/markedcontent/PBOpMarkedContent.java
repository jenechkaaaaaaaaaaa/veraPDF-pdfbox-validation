package org.verapdf.model.impl.pb.operator.markedcontent;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.verapdf.model.coslayer.CosDict;
import org.verapdf.model.coslayer.CosName;
import org.verapdf.model.impl.pb.cos.PBCosDict;
import org.verapdf.model.impl.pb.cos.PBCosName;
import org.verapdf.model.impl.pb.operator.base.PBOperator;
import org.verapdf.model.operator.OpMarkedContent;
import org.verapdf.pdfa.flavours.PDFAFlavour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for marked content operators
 *
 * @author Timur Kamalov
 */
public abstract class PBOpMarkedContent extends PBOperator implements
        OpMarkedContent {

	private final PDDocument document;
	private final PDFAFlavour flavour;

	/** Name of link to the tag name */
    public static final String TAG = "tag";
	/** Name of link to the properties dictionary */
    public static final String PROPERTIES = "properties";

    public PBOpMarkedContent(List<COSBase> arguments, final String opType, PDDocument document, PDFAFlavour flavour) {
        super(arguments, opType);
		this.document = document;
		this.flavour = flavour;
    }

    protected List<CosName> getTag() {
        if (this.arguments.size() > 1) {
			COSBase name = this.arguments
					.get(this.arguments.size() - 2);
			if (name instanceof COSName) {
				List<CosName> list =
						new ArrayList<>(MAX_NUMBER_OF_ELEMENTS);
				list.add(new PBCosName((COSName) name));
				return Collections.unmodifiableList(list);
			}
        }
        return Collections.emptyList();
    }

    protected List<CosDict> getPropertiesDict() {
        if (!this.arguments.isEmpty()) {
			COSBase dict = this.arguments
					.get(this.arguments.size() - 1);
			if (dict instanceof COSDictionary) {
				List<CosDict> list =
						new ArrayList<>(MAX_NUMBER_OF_ELEMENTS);
				list.add(new PBCosDict((COSDictionary) dict, document, flavour));
				return Collections.unmodifiableList(list);
			}
        }
        return Collections.emptyList();
    }

}
