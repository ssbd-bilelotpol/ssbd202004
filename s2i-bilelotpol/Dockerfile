# bilelotpol-build
FROM openshift/base-centos7

LABEL maintainer="Adam Zambrzycki <adam@zambrzycki.net>"
LABEL io.k8s.description="Platform for building bilelotpol" \
      io.k8s.display-name="Bilelotpol 1.0.0"

RUN curl -sL https://rpm.nodesource.com/setup_15.x | bash -
RUN yum -y install java-11-openjdk-devel maven nodejs && yum clean all -y

COPY ./s2i/bin/ /usr/libexec/s2i

RUN chown -R 1001:1001 /opt/app-root
USER 1001

CMD ["/usr/libexec/s2i/usage"]
