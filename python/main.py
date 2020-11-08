import xml.dom.minidom

file = "../src/main/webapp/WEB-INF/glassfish-web.xml"

doc = xml.dom.minidom.parse(file)
elements = doc.getElementsByTagName("security-role-mapping")

a_dict = {}

output = ""

for role in elements:
    name = role.getElementsByTagName("role-name")[0].firstChild.nodeValue
    groups = [it.firstChild.nodeValue for it in role.getElementsByTagName("group-name")]
    for group in groups:
        if group not in a_dict:
            a_dict[group] = ""
        a_dict[group] += "%s," % name

for (key, val) in a_dict.items():
    print("%s = %s\n" % (key, val))
