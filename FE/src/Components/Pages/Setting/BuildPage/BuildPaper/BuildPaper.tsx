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
import BuildProperty from '../PropertyPaper/PropertyPaper';

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

export default function BuildPaper({ index, buildData, DelClick }: buildProps) {
  const frameworkItems = useDropdownStore((state) => state.framwork);

  const [name, setName] = useState(buildData.name);
  const [error, setError] = useState(false);
  const [fileDir, setFileDir] = useState(buildData.projectDirectory);
  const [buildPath, setBuildPath] = useState(buildData.buildPath);

  const [framework, setFramework] = useState('');
  const [versionName, setVersionName] = useState('');
  const [version, setVersion] = useState(buildData.version);
  const [type, setType] = useState(buildData.type);

  const [frameworks, setFrameworks] = useState<string[]>([]);
  const [versions, setVersions] = buildData.version
    ? useState<string[]>([buildData.version])
    : useState<string[]>([]);
  const [types, setTypes] = buildData.type
    ? useState<string[]>([buildData.type])
    : useState<string[]>([]);

  const handleNameOnChange = (event: any) => {
    const regex = /^[a-z0-9\\_]*$/;
    if (regex.test(event.target.value)) {
      setName(event.target.value);
      buildData.name = event.target.value;
      setError(false);
    } else {
      setName('');
      buildData.name = '';
      setError(true);
    }
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

  const handlePropsFrameworkChange = (event: string) => {
    setFramework(event);
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
  const handlePropsFrameworkClick = (index: number) => {
    buildData.frameworkId = frameworkItems[index].id;

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
    const data = frameworkItems.map((value) => {
      if (value.id === buildData.frameworkId) setFramework(value.name);
      return value.name;
    });
    setFrameworks(data);

    if (buildData.frameworkId !== 0) {
      const params = { typeId: buildData.frameworkId };
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
      setFrameworks([]);
      setVersions([]);
      setTypes([]);
    };
  }, [frameworkItems]);

  return (
    <Box>
      <Grid container spacing={1}>
        <Grid item xs={6}>
          <Typography>Name</Typography>
          <TextField
            fullWidth
            error={error}
            helperText={error ? '명칭을 규칙에 맞게 적어주세요' : ''}
            defaultValue={name}
            label="Name"
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            placeholder="ex) lowercase, 숫자, _ 만 사용가능"
            onChange={handleNameOnChange}
          />
        </Grid>
        <Grid item xs={2}>
          <Typography>Framework</Typography>
          <SelectItem
            defaultValue={framework}
            label="Framework/ Library"
            Items={frameworks}
            Change={handlePropsFrameworkChange}
            Click={handlePropsFrameworkClick}
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
            {framework === 'Vue' || framework === 'React'
              ? 'Nginx Use'
              : 'Type'}
          </Typography>
          <SelectItem
            defaultValue={type}
            label="Types"
            Items={types}
            Change={handlePropsTypeChange}
          />
          {framework === 'Vue' || framework === 'React' ? (
            <FormHelperText id="component-helper-text">
              (※ Yes는 하나만)
            </FormHelperText>
          ) : (
            <Box />
          )}
        </Grid>
        <Grid item xs={12}>
          <Box />
          <Typography>Project File Dir</Typography>
          <TextField
            label="ex) /FE , /BE"
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
          <Typography>Build File Path</Typography>
          <TextField
            label="ex) /dist, /build"
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
      <BuildProperty buildValue={buildData} />
    </Box>
  );
}
