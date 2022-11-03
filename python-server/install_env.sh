conda create -n furhat_server python=3.6.10
eval "$(conda shell.bash hook)"
conda activate furhat_server
conda install -c pytorch-lts -c nvidia -c pytorch torchvision torchaudio cudatoolkit=11.1
conda install submitit pandas -c conda-forge
pip install transformers==4.18.0
pip install textblob