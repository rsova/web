package utils

import org.apache.http.entity.ContentType

class MimeTypeUtil {
	static final List XML_MIME_TYPES = [ContentType.APPLICATION_XML.mimeType, ContentType.APPLICATION_ATOM_XML.mimeType, ContentType.APPLICATION_XHTML_XML.mimeType, ContentType.TEXT_XML.mimeType]
	
}
