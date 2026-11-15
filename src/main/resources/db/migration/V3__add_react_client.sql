INSERT INTO oauth2_registered_client (
    id,
    client_id,
    client_id_issued_at,
    client_secret,
    client_secret_expires_at,
    client_name,
    client_authentication_methods,
    authorization_grant_types,
    redirect_uris,
    post_logout_redirect_uris,
    scopes,
    client_settings,
    token_settings
)
VALUES (
   gen_random_uuid()::text,
   'react-spa-client',
   CURRENT_TIMESTAMP,
   NULL,
   NULL,
   'React SPA',
   'none',
   'authorization_code',
   'http://localhost:3000/callback',
   NULL,
   'system-read',
   '{
     "@class": "java.util.Collections$UnmodifiableMap",
     "settings.client.require-proof-key": true,
     "settings.client.require-authorization-consent": true
   }',
   '{
     "@class": "java.util.Collections$UnmodifiableMap",
     "settings.token.access-token-format": {
       "@class": "org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat",
       "value": "reference"
     }
   }'
);