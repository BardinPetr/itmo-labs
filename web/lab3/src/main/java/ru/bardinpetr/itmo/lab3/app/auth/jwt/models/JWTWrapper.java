// Generated by delombok at Thu May 09 13:42:06 MSK 2024
package ru.bardinpetr.itmo.lab3.app.auth.jwt.models;

public class JWTWrapper {
    private JWTType type;
    private String token;

    @java.lang.SuppressWarnings("all")
    public JWTType getType() {
        return this.type;
    }

    @java.lang.SuppressWarnings("all")
    public String getToken() {
        return this.token;
    }

    @java.lang.SuppressWarnings("all")
    public void setType(final JWTType type) {
        this.type = type;
    }

    @java.lang.SuppressWarnings("all")
    public void setToken(final String token) {
        this.token = token;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof JWTWrapper)) return false;
        final JWTWrapper other = (JWTWrapper) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$type = this.getType();
        final java.lang.Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final java.lang.Object this$token = this.getToken();
        final java.lang.Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof JWTWrapper;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final java.lang.Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "JWTWrapper(type=" + this.getType() + ", token=" + this.getToken() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public JWTWrapper(final JWTType type, final String token) {
        this.type = type;
        this.token = token;
    }

    @java.lang.SuppressWarnings("all")
    public JWTWrapper() {
    }
}