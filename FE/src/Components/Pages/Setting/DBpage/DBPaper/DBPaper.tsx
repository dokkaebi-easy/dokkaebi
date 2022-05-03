import { useEffect, useState } from 'react';
import Grid from '@mui/material/Grid';
import SelectItem from 'Components/UI/Atoms/SelectItem/SelectItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Box from '@mui/material/Box';
import FormHelperText from '@mui/material/FormHelperText';
import { Build } from 'Components/MDClass/BuildData/BuildData';
import axios from 'axios';
import { useDropdownStore } from 'Components/Store/DropDownStore/DropDownStore';

interface VersionTypeAxois {
  name: string;
  buildTool: string[];
  frameworkVersion: string[];
}

interface buildProps {
  index: number;
  buildData: Build;
  DelClick: (value: number) => void;
}

export default function DBPaper({ index, buildData, DelClick }: buildProps) {
  const framAndLibsDatas = useDropdownStore((state) => state.framworkandLib);

  const [name, setName] = useState(buildData.name);
  const [fileDir, setFileDir] = useState(buildData.projectDirectory);
  const [buildPath, setBuildPath] = useState(buildData.buildPath);
  const [frameworkId, setFrameworkId] = useState(buildData.frameworkId);

  const [frameworkName, setFrameworkName] = useState('');
  const [version, setVersion] = useState(buildData.version);
  const [type, setType] = useState(buildData.type);

  const [framAndLibs, setFramAndLibs] = useState<string[]>([]);
  const [versionName, setVersionName] = useState('');
  const [versions, setVersions] = useState<string[]>([]);
  const [types, setTypes] = useState<string[]>([]);

  const handleNameOnChange = (event: any) => {
    setName(event.target.value);
    buildData.name = event.target.value;
  };

  const handleFileDirOnChange = (event: any) => {
    setFileDir(event.target.value);
    buildData.projectDirectory = event.target.value;
  };
  const handlebuildPathOnChange = (event: any) => {
    setBuildPath(event.target.value);
    buildData.buildPath = event.target.value;
  };

  const handleDelOnClick = () => {
    DelClick(index);
  };

  const handlePropsFLChange = (event: string) => {
    setFrameworkName(event);
    buildData.version = '';
    setVersion('');
    buildData.type = '';
    setType('');
  };

  const handlePropsVersionChange = (event: string) => {
    setVersion(event);
    buildData.version = event;
  };
  const handlePropsTypeChange = (event: string) => {
    setType(event);
    buildData.type = event;
  };
  const handleFrameworkClickProps = (index: number) => {
    setFrameworkId(framAndLibsDatas[index].id);
    buildData.frameworkId = framAndLibsDatas[index].id;

    const params = { typeId: buildData.frameworkId };
    axios
      .get('/api/project/frameworkVersion', { params })
      .then((res) => {
        const data = res.data as VersionTypeAxois;

        setVersionName(data.name);
        setVersion('');
        setType('');
        setVersions([...data.frameworkVersion]);
        setTypes([...data.buildTool]);
      })
      .catch();
  };

  useEffect(() => {
    const data = framAndLibsDatas.map((value) => {
      if (value.id === buildData.frameworkId) setFrameworkName(value.name);
      return value.name;
    });
    setFramAndLibs(data);

    if (buildData.frameworkId !== -1) {
      const params = { typeId: frameworkId };
      axios
        .get('/api/project/frameworkVersion', { params })
        .then((res) => {
          const data = res.data as VersionTypeAxois;
          setVersionName(data.name);
          setVersions([...data.frameworkVersion]);
          setTypes([...data.buildTool]);
        })
        .catch();
    }

    return () => {
      setFramAndLibs([]);
      setVersions([]);
      setTypes([]);
    };
  }, []);

  return (
    <Box>
      <Grid container spacing={1}>
        <Grid item xs={6}>
          <Typography>Name</Typography>
          <TextField
            fullWidth
            defaultValue={name}
            label="Name"
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Name"
            onChange={handleNameOnChange}
          />
        </Grid>
        <Grid item xs={2}>
          <Typography>DB</Typography>
          <SelectItem
            defaultValue={frameworkName}
            label="Framework/ Library"
            Items={framAndLibs}
            Change={handlePropsFLChange}
            Click={handleFrameworkClickProps}
          />
        </Grid>
        <Grid item xs={2}>
          <Typography>{versionName} Version</Typography>
          <SelectItem
            defaultValue={version}
            label="Versions"
            Items={versions}
            Change={handlePropsVersionChange}
          />
        </Grid>
        <Grid item xs={2}>
          <Typography>
            {frameworkName === 'Vue' || frameworkName === 'React'
              ? 'Nginx Use'
              : 'Type'}
          </Typography>
          <SelectItem
            defaultValue={type}
            label="Types"
            Items={types}
            Change={handlePropsTypeChange}
          />
          {frameworkName === 'Vue' || frameworkName === 'React' ? (
            <FormHelperText id="component-helper-text">
              (※ Yes는 하나만)
            </FormHelperText>
          ) : (
            <Box />
          )}
        </Grid>
        <Grid item xs={12}>
          <Box />
          <Typography>Port</Typography>
          <TextField
            label="Dir"
            fullWidth
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Dir"
            defaultValue={fileDir}
            onChange={handleFileDirOnChange}
          />
        </Grid>
        <Grid item xs={12}>
          <Typography>Dump File Dir</Typography>
          <TextField
            label="Dir"
            fullWidth
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="Dir"
            defaultValue={buildPath}
            onChange={handlebuildPathOnChange}
          />
        </Grid>
        <Grid item xs={2}>
          <Button
            variant="outlined"
            startIcon={<DeleteIcon />}
            onClick={handleDelOnClick}
            sx={{
              color: 'red',
              borderColor: 'red',
            }}
          >
            Delete
          </Button>
        </Grid>
      </Grid>
    </Box>
  );
}
