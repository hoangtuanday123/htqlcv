import uvicorn
from configs import configs

if __name__ == "__main__":
    uvicorn.run(
        "app:app",
        host=configs.HOST,
        reload=configs.DEBUG_MODE,
        port=configs.PORT,
    )