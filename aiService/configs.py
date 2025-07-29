import os
from dotenv import load_dotenv, find_dotenv
from pathlib import Path

env_prod = find_dotenv('.env.prod')

if env_prod:
    load_dotenv(dotenv_path=env_prod)
else:
    load_dotenv()

class Configs:
    PROJECT_NAME: str = "AI SERVICE"
    PROJECT_VERSION: str = "1.0.0"
    HOST: str = os.getenv("HOST", "localhost")
    PORT: int = int(os.getenv("PORT", "8080"))
    ENV: str = os.getenv("ENV")
    DEBUG_MODE: bool = bool(os.getenv("DEBUG_MODE", 'True'))
    MYSQL_HOST: str = os.getenv("MYSQL_HOST", "localhost")
    MYSQL_USER: str = os.getenv("MYSQL_USER", "root")
    MYSQL_PASSWORD: str = os.getenv("MYSQL_PASSWORD", "12345678")
    MYSQL_PORT: int = int(os.getenv("MYSQL_PORT", 3306))
    MYSQL_DB: str = os.getenv("MYSQL_DB", "htqlcv")
    LANGSMITH_TRACING: str = os.getenv("LANGSMITH_TRACING")
    LANGSMITH_API_KEY: str = os.getenv("LANGSMITH_API_KEY")
    LANGSMITH_PROJECT: str = os.getenv("LANGSMITH_PROJECT")
    GOOGLE_API_KEY: str = os.getenv("GOOGLE_API_KEY")


configs = Configs()