import xml.dom.minidom

file = "../src/main/webapp/WEB-INF/glassfish-web.xml"

doc = xml.dom.minidom.parse(file)
elements = doc.getElementsByTagName("security-role-mapping")

output = ""

for role in elements:
    name = role.getElementsByTagName("role-name")[0].firstChild.nodeValue
    groups = [it.firstChild.nodeValue for it in role.getElementsByTagName("group-name")]
    output += "<security-role name=\"%s\">" % name
    for group in groups:
        output += "<group name=\"%s\"/>" % group
    output += "</security-role>\n"

print(output)
