package org.apache.poi.xwpf.converter.styles.pargraph;

import org.apache.poi.xwpf.converter.styles.XWPFStylesDocument;
import org.apache.poi.xwpf.usermodel.BodyType;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocDefaults;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;

public abstract class AbstractSpacingParagraphValueProvider<Value>
    extends AbstractParagraphValueProvider<Value>
{
    @Override
    public Value getValueFromElement( XWPFParagraph paragraph )
    {
        return internalGetValue( getSpacing( getCTPPr( paragraph ) ) );
    }

    @Override
    protected Value getValueFromStyle( CTStyle style )
    {
        return internalGetValue( getSpacing( getCTPPr( style ) ) );
    }

    @Override
    protected Value getValueFromDocDefaultsStyle( CTDocDefaults docDefaults )
    {
        return internalGetValue( getSpacing( getCTPPr( docDefaults ) ) );
    }

    private Value internalGetValue( CTSpacing ind )
    {
        if ( ind == null )
        {
            return null;
        }
        return getValue( ind );
    }

    public CTSpacing getSpacing( CTPPr pr )
    {
        if ( pr == null )
        {
            return null;
        }
        return pr.getSpacing() == null ? null : pr.getSpacing();
    }

    @Override
    protected String getKey( XWPFParagraph element, XWPFStylesDocument stylesDocument, String styleId )
    {
        if ( element.getPartType() == BodyType.TABLECELL )
        {
            if ( styleId != null && styleId.length() > 0 )
            {
                return new StringBuilder( this.getClass().getName() ).append( "_" ).append( styleId ).append( "_cell" ).toString();
            }
            return new StringBuilder( this.getClass().getName() ).append( "_cell" ).toString();
        }
        return super.getKey( element, stylesDocument, styleId );
    }

    protected abstract Value getValue( CTSpacing ind );

}
